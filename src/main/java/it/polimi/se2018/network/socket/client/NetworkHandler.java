package it.polimi.se2018.network.socket.client;

public class NetworkHandler extends Thread implements ServerSocketInterface {

    private Socket socket;
    private BufferedReader is;

    private ClientInterface client;

    public NetworkHandler(String host, int port, ClientInterface client) {

        try {
            this.socket =  new Socket( host, port);

            System.out.println("Connected.");

            this.is = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.client = client;

            this.start();

        } catch (IOException e) {
            System.out.println("Connection Error.");
            e.printStackTrace();
        }

    }

    @Override
    public void run () {

        System.out.println("Listening for messages from the Server. ");

        boolean loop = true;
        while ( loop && !this.socket.isClosed() ) {

            try {

                String message = this.is.readLine();

                if ( message == null ) {
                    loop = false;
                    this.stopConnection();

                } else {
                    client.notify(new Message(message));
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public synchronized void send ( Message message ) {

        try {

            OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());

            // qui passiamo la stringa, ma potete passare anche l'oggetto Message
            // serializzato e deserializzarlo lato Server ( online troverete come fare )

            writer.write(message.getMessage());
            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void stopConnection () {

        if ( !socket.isClosed() ) {
            try {
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Connection closed.");
        }
    }
}
