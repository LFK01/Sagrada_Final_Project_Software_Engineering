package it.polimi.se2018.model;
import static org.junit.Assert.*;
/**
 * @author giovanni
 */

import org.junit.Test;

public class TestSchemaCard {
    @Test
    public void testSchemaCard(){
        int n = 1;
        int a=0;
        int b=0;
        SchemaCard schemaCard = null;
        try{
            schemaCard = new SchemaCard(n);
        }
        catch (NullPointerException e){
            System.out.println("ciao Luciano");
        }
        //fail();
        assertEquals(4,schemaCard.getCell(a,b).getValue());



    }

}
