package mmdb.kvs.app.resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;


import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.codahale.metrics.annotation.Timed;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponses;
import com.wordnik.swagger.annotations.ApiResponse;

import mmdb.kvs.app.output.pair;
import mmdb.kvs.app.output.pairs;
import scala.collection.immutable.Map.Map2;

import java.io.BufferedReader;
//import java.io.Console;
//import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Character.Subset;

@Path("basicpairs")
@Api(value = "basicpairs", description = "Basic Pairs API")
@Produces(MediaType.APPLICATION_JSON)
public class basicPairsResource {
	public static ConcurrentNavigableMap<String,Map<String, String>> map;
	private static List<String> fields;
	private static final String tableName="User";

    public basicPairsResource() {
    	//String csvFile = "/Users/sindhuja/eclipse-workspace/mmdb/src/main/java/mmdb/kvs/app/resources/dataset.csv";
        //String line = "";
       // String seperator = " ";
        
//    	try(BufferedReader br = new BufferedReader(new FileReader(csvFile))){
        this.map = new ConcurrentSkipListMap<String, Map<String,String>>();
        String[] array = new String[] { "field0", "field1", "field2", "field3", "field4", "field5", "field6", "field7", "field8", "field9"};
        this.fields= Arrays.asList(array);
//        while((line = br.readLine()) != null){
//        //	String[] id = line.split(seperator);
//    	//	String attributeID = id[0].substring(1);
//    	//	String attributeValue =         
//    		map.put(map.entrySet().size(), line);
//    		
//    		
//        }
      //String lineNumber = line.toString();
		//String[] id = lineNumber.split(seperator,2);
		//System.out.println(id);
		//for(String l:id){
		//String[] attributeID = l.split(seperator,2);
		
		
		//map.put(attributeID.toString(), "h");
        		//System.out.println(id[0]+id[1]);
        		//map.put("Team TAM:", "Number 1");
                //map.put("Team someone else:", "Number 2");
        		
        		
        		
        
        		
        	
//        }
//    	
//    	
//        catch(IOException e){
//        	e.printStackTrace();
//        }
    	
    	
    	
    }
    
  //read the csv file
    //access first column of the csv file
    //put that in 1st string
    //access all the other columns
    //put them in json object
    //convert the json into string
    //put them in 2nd string of concurrent skiplist map
    @GET
    @Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get all pairs",
			notes = "Description here.",
			response = pairs.class)
    @Timed
    public Response getAllPairs() {
    		List<pair> pairList = new ArrayList<pair>();
    		for (Map.Entry<String, Map<String, String>> entry : map.entrySet())
    		{
    			pair tuple = new pair();
    			tuple.setKey(entry.getKey());
    			tuple.setValue(entry.getValue());
    			pairList.add(tuple);
    		 }
    		/*map.keySet().forEach(it->{
    			pair tuple = new pair();
    			tuple.setKey(it);
    			tuple.setValue(map.get(it));
    			pairList.add(tuple);
    		});*/
    		pairs result = new pairs(pairList);
    		return Response.ok(result).build();         
    	}

//get a single pair based on the given key 
    @GET
    @Path("/{key}")
    @Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get all pairs",
			notes = "Description here.",
			response = pairs.class)
    @Timed
    public Response getPair(@PathParam(value="key") String key, @QueryParam(value="table") String table) {
    		pair tuple = new pair();
    		if (map.containsKey(key)) {
    			Map<String, String> entry= map.get(key);
    			tuple.setKey(key);
    			tuple.setValue(entry);
    		}
    		return Response.ok(tuple).build();         
    	}
    
//insert operator
	@POST
	@Path("/insert")
	@Consumes(MediaType.APPLICATION_JSON)
	@Timed
	@ApiOperation(value = "Create a new entry",
			notes = "Description here.")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Created"),
			@ApiResponse(code = 400, message = "Bad Request - invalid data has been "
					+ "passed in the request body"),
			@ApiResponse(code = 500, message = "Internal server error")
		})
	public Response insert(
			@QueryParam(value="table") String table,
	@QueryParam(value="key")String key, 
	@QueryParam(value="field0") String field0,
	@QueryParam(value="field1") String field1,	
	@QueryParam(value="field2") String field2,	
	@QueryParam(value="field3") String field3,
	@QueryParam(value="field4") String field4,
	@QueryParam(value="field5") String field5,
	@QueryParam(value="field6") String field6,
	@QueryParam(value="field7") String field7,
	@QueryParam(value="field8") String field8,
	@QueryParam(value="field9") String field9) {
		
		pair pair = new pair();
		pair.setKey(key);
		Map<String, String> value = new HashMap<>();
		value.put("field0", field0);
		value.put("field1", field1);
		value.put("field2", field2);
		value.put("field3", field3);
		value.put("field4", field4);
		value.put("field5", field5);
		value.put("field6", field6);
		value.put("field7", field7);
		value.put("field8", field8);
		value.put("field9", field9);
		pair.setValue(value);
		table = tableName;
		if (table==null||!table.equals(tableName)){
		    System.out.println("error 1");
			return Response.status(Status.BAD_REQUEST).build();
		}
		if (map.containsKey(pair.getKey())){
			System.out.println("error 2");
			return Response.status(Status.BAD_REQUEST).build();
		}
		if (pair.getValue().entrySet().size()!=10){
		System.out.println("error 3");
			return Response.status(Status.BAD_REQUEST).build();
		}
		if (!pair.getValue().keySet().containsAll(fields)){
			System.out.println("error 4");
			return Response.status(Status.BAD_REQUEST).build();
		}
		try{		
			map.put(pair.getKey(), pair.getValue());
		}
		catch(Exception e){
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Status.CREATED).build();         
	}
	
	// delete operator
	@DELETE
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Timed
	@ApiOperation(value = "Delete an existing entry",
			notes = "Description here.")
	@ApiResponses(value = {
			@ApiResponse(code = 202, message = "Deleted"),
			@ApiResponse(code = 400, message = "Bad Request - invalid data has been "
					+ "passed in the request body"),
			@ApiResponse(code = 500, message = "Internal server error")
		})
	public Response delete(@QueryParam(value="table")
	String table, @QueryParam(value="key")String key) {
		
		if (table==null||!table.equals(tableName)){
		    System.out.println("error 1");
			return Response.status(Status.BAD_REQUEST).build();
		}
	   if (!map.containsKey(key)){
			System.out.println("error 2");
			return Response.status(Status.BAD_REQUEST).build();
		}
		try{		
			map.remove(key);
		}
		catch(Exception e){
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Status.GONE).build();         
	}
	
	//update operator
	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Timed
	@ApiOperation(value = "Update an existing entry",
			notes = "Description here.")
	@ApiResponses(value = {
			@ApiResponse(code = 203, message = "Updated"),
			@ApiResponse(code = 204, message = "Not Modified - Please enter an existing key"),
			@ApiResponse(code = 400, message = "Bad Request - invalid data has been "
					+ "passed in the request body"),
			@ApiResponse(code = 500, message = "Internal server error")
		})
	public Response update(@QueryParam(value="table") String table,
			@QueryParam(value="key")String key, 
			@QueryParam(value="field0") String field0,
			@QueryParam(value="field1") String field1,	
			@QueryParam(value="field2") String field2,	
			@QueryParam(value="field3") String field3,
			@QueryParam(value="field4") String field4,
			@QueryParam(value="field5") String field5,
			@QueryParam(value="field6") String field6,
			@QueryParam(value="field7") String field7,
			@QueryParam(value="field8") String field8,
			@QueryParam(value="field9") String field9) {
		
		    pair pair = new pair();
		    pair.setKey(key);
		    Map<String, String> value = new HashMap<>();
		    value.put("field0", field0);
		    value.put("field1", field1);
		    value.put("field2", field2);
		    value.put("field3", field3);
		    value.put("field4", field4);
		    value.put("field5", field5);
		    value.put("field6", field6);
		    value.put("field7", field7);
		    value.put("field8", field8);
		    value.put("field9", field9);
		    pair.setValue(value);
		    table = tableName;
		
		if (table==null||!table.equals(tableName)){
		    //System.out.println("error 1");
			return Response.status(Status.BAD_REQUEST).build();
		}
		if (!map.containsKey(pair.getKey())){
			//System.out.println("error 2");
			return Response.status(Status.NOT_MODIFIED).build();
		}
		if (pair.getValue().entrySet().size()!=10){
			//System.out.println("error 3");
			return Response.status(Status.BAD_REQUEST).build();
		}
		if (!pair.getValue().keySet().containsAll(fields)){
			//System.out.println("error 4");
			return Response.status(Status.BAD_REQUEST).build();
		}
		try{		
			map.put(pair.getKey(), pair.getValue());
		}
		catch(Exception e){
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Status.OK).build();         
	}


//scan operator 

@GET
@Path("/scan")
@Produces(MediaType.APPLICATION_JSON)
@ApiOperation(value = "Get all the scanned pairs",
		notes = "Description here.",
		response = pairs.class)
@Timed
public Response getScannedPairs(@QueryParam(value="table") String table, @QueryParam(value="key") String key, @QueryParam(value="length") Integer length) {
		List<pair> pairList = new ArrayList<pair>();
		if (map.containsKey(key)) {
			Iterator it = map.entrySet().iterator();
			boolean foundKey= false;
			Integer counter = 0;
			while (it.hasNext() && counter<length) {
				Map.Entry<String, Map<String, String>> entry = (Entry<String, Map<String, String>>) it.next();
				System.out.println("KEY:"+entry.getKey());
				if (!foundKey) {
					if (entry.getKey().equals(key)) {
						pair tuple = new pair();
						tuple.setKey(key);
						tuple.setValue(entry.getValue());
						pairList.add(tuple);
						foundKey=true;
						counter++;
					}
				}
				else{
					if (counter<length) {
						pair tuple = new pair();
						tuple.setKey(entry.getKey());
						tuple.setValue(entry.getValue());
						pairList.add(tuple);
						counter++;
					}
				}	
			}
			
		}
		pairs result = new pairs(pairList);
		return Response.ok(result).build();          
	}
}




		