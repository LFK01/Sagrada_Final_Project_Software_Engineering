package it.polimi.se2018.model.events.messages;

public class SendSchemaAndTurn extends Message{
    private String schema1;
    private String schema2;
    private String schema3;
    private String schema4;

    public SendSchemaAndTurn(String sender, String recipient,String schema1,String schema2, String schema3,String schema4) {
        super(sender, recipient);
        this.schema1 = schema1;
        this.schema2 = schema2;
        this.schema3 = schema3;
        this.schema4 = schema4;

    }

    public SendSchemaAndTurn(String sender, String recipient,String schema1,String schema2, String schema3) {
        super(sender, recipient);
        this.schema1 = schema1;
        this.schema2 = schema2;
        this.schema3 = schema3;

    }
    public SendSchemaAndTurn(String sender,String recipient,String schema1,String schema2){
        super(sender,recipient);
        this.schema1 = schema1;
        this.schema2 = schema2;
    }


    public String getSchema1() {
        return schema1;
    }

    public String getSchema2() {
        return schema2;
    }

    public String getSchema3() {
        return schema3;
    }

    public String getSchema4() {
        return schema4;
    }
}
