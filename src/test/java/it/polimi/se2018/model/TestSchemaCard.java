package it.polimi.se2018.model;
/**
 * @author giovanni
 */

import org.junit.Test;

public class TestSchemaCard {
    @Test
    public void testSchemaCard(){
        int n = 6;
        SchemaCard schemaCard = null;
        try{
            schemaCard = new SchemaCard(n);
        }
        catch (NullPointerException e){
            System.out.println("ciao Luciano");
        }



    }

}
