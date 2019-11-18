import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

public class Main {
    public static class HttpServer {
        public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webRoot";
        private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";
        private boolean shutdown = false;

        public void await() {
            ServerSocket serverSocket = null;
            int port = 8080;
            System.out.println(WEB_ROOT);
            try{
                serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
            }
            catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }

            while(!shutdown){
                Socket socket = null;
                InputStream input = null;
                OutputStream output = null;

                try{
                    socket = serverSocket.accept();
                    input = socket.getInputStream();
                    output = socket.getOutputStream();

                    Request request = new Request(input);
                    request.parse();

                    Response response = new Response(output);
                    response.setRequest(request);
                    response.sendStaticResource();

                    socket.close();

                    shutdown = request.getUri().equals(SHUTDOWN_COMMAND);
                }
                catch (Exception e){
                    e.printStackTrace();
                    continue;
                }
            }
        }
    }

    public static class Request {
        private InputStream input;
        private String uri;

        public Request(InputStream input) { this.input = input;}

        public void parse() {
            StringBuffer request = new StringBuffer(2048);
            int i;
            byte[] buffer = new byte[2048];
            try {
                i = input.read(buffer);
            }
            catch (IOException e){
                e.printStackTrace();
                i = -1;
            }

            for(int j = 0 ; j < i ; j++){
                request.append((char)buffer[j]);
            }

            System.out.print(request.toString());
            uri = parseUri(request.toString());

        }

        public String parseUri(String requestString) {
            int index1, index2;
            index1 = requestString.indexOf(' ');

            if(index1 != -1){
                index2 = requestString.indexOf(' ', index1 + 1);
                if(index2 > index1)
                    return requestString.substring(index1 + 1, index2);
            }

            return null;

        }
        public String getUri() { return uri; }
    }

    public static class Response {
        private static final int BUFFER_SIZE = 1024;
        Request request;
        OutputStream output;

        public Response(OutputStream output){
            this.output = output;
        }

        public void setRequest(Request request){
            this.request = request;
        }

        public void sendStaticResource() throws IOException {
            byte[] bytes = new byte[BUFFER_SIZE];
            FileInputStream fis = null;

            try {
                File file = new File(HttpServer.WEB_ROOT, request.getUri());
                if (file.exists()){
                    fis = new FileInputStream(file);
                    int ch = fis.read(bytes, 0, BUFFER_SIZE);
                    while(ch != -1){
                        output.write(bytes, 0, ch);
                        ch = fis.read(bytes, 0, BUFFER_SIZE);
                    }
                }
                else {
                    String errorMessage = "HTTP/1.1 404 File Not Found\r\n" +
                            "Content-Type: text/html\r\n" +
                            "Content-Length: 23\r\n" +
                            "\r\n" +
                            "<h1>File Not Found</h1>";
                    output.write(errorMessage.getBytes());
                }
            }
            catch (Exception e){
                System.out.println(e.toString());
            }
            finally {
                if(fis != null ) fis.close();
            }
        }
    }

    public static void main(String[] args) {
        HttpServer server = new HttpServer();
        server.await();
    }
}
