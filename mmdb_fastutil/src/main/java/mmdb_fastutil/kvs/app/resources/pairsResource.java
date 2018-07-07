package mmdb_fastutil.kvs.app.resources;

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

import mmdb_fastutil.kvs.app.output.FineGrainedPair;
import mmdb_fastutil.kvs.app.output.FineGrainedPairs;
import it.unimi.dsi.fastutil.*;
//import scala.Tuple2;
//import scala.collection.immutable.Map.Map2;
//import scala.collection.mutable.OpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.*;

import java.io.BufferedReader;
//import java.io.Console;
//import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Character.Subset;

@Path("pairs")
@Api(value = "FineGrainedPairs", description = "Pairs API")
@Produces(MediaType.APPLICATION_JSON)
public class pairsResource {
	//public static ConcurrentNavigableMap<String,Map<String, String>> map;
	public static Object2ObjectOpenHashMap<String, Map<String, String>> map;
	private static List<String> fields;
	private static final String tableName="User";

    public pairsResource() {
    	
        //this.map = new ConcurrentSkipListMap<String, Map<String,String>>();
    	this.map = new Object2ObjectOpenHashMap<String, Map<String, String>>();
        String[] array = new String[] { "field0", "field1", "field2", "field3", "field4", "field5", "field6", "field7", "field8", "field9"};
        this.fields= Arrays.asList(array);
    		
    }
    
    @SuppressWarnings("unchecked")
	@GET
    @Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get all pairs",
			notes = "Description here.",
			response = FineGrainedPairs.class)
    @Timed
    public Response getAllPaFineGrainedPairs() {
    		List<FineGrainedPair> pairList = new ArrayList<FineGrainedPair>();
    		
    		
    		//for (Map.Entry<String, Map<String, String>> entry : map.entrySet())
    		for (it.unimi.dsi.fastutil.objects.Object2ObjectMap.Entry<String, Map<String, String>> entry: this.map.object2ObjectEntrySet()){
    			FineGrainedPair tuple = new FineGrainedPair();
    			tuple.setKey(entry.getKey());
    			tuple.setField0(entry.getValue().get("field0"));
    			tuple.setField1(entry.getValue().get("field1"));
    			tuple.setField2(entry.getValue().get("field2"));
    			tuple.setField3(entry.getValue().get("field3"));
    			tuple.setField4(entry.getValue().get("field4"));
    			tuple.setField5(entry.getValue().get("field5"));
    			tuple.setField6(entry.getValue().get("field6"));
    			tuple.setField7(entry.getValue().get("field7"));
    			tuple.setField8(entry.getValue().get("field8"));
    			tuple.setField9(entry.getValue().get("field9"));
    			pairList.add(tuple);
    		 }
    		/*map.keySet().forEach(it->{
    			pair tuple = new pair();
    			tuple.setKey(it);
    			tuple.setValue(map.get(it));
    			pairList.add(tuple);
    		});*/
    		FineGrainedPairs result = new FineGrainedPairs(pairList);
    		return Response.ok(result).build();         
    	}

//get a single pair based on the given key 
    @GET
    @Path("/{key}")
    @Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get all pairs",
			notes = "Description here.",
			response = FineGrainedPairs.class)
    @Timed
    public Response getPair(@PathParam(value="key") String key, @QueryParam(value="table") String table) {
    	FineGrainedPair tuple = new FineGrainedPair();
    		if (map.containsKey(key)) {
    			Map<String, String> entry= (Map<String, String>) map.get(key);
    			tuple.setKey(key);
    			tuple.setField0(entry.get("field0"));
    			tuple.setField1(entry.get("field1"));
    			tuple.setField2(entry.get("field2"));
    			tuple.setField3(entry.get("field3"));
    			tuple.setField4(entry.get("field4"));
    			tuple.setField5(entry.get("field5"));
    			tuple.setField6(entry.get("field6"));
    			tuple.setField7(entry.get("field7"));
    			tuple.setField8(entry.get("field8"));
    			tuple.setField9(entry.get("field9"));
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
		
		FineGrainedPair pair = new FineGrainedPair();
		pair.setKey(key);
		
		Map<String, String> value = new HashMap<String, String>();
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
		pair.setField0(field0);
		pair.setField1(field1);
		pair.setField2(field2);
		pair.setField3(field3);
		pair.setField4(field4);
		pair.setField5(field5);
		pair.setField6(field6);
		pair.setField7(field7);
		pair.setField8(field8);
		pair.setField9(field9);
		
		table = tableName;
		if (table==null||!table.equals(tableName)){
		    System.out.println("error 1");
			return Response.status(Status.BAD_REQUEST).build();
		}
		if (map.containsKey(pair.getKey())){
			System.out.println("error 2");
			return Response.status(Status.BAD_REQUEST).build();
		}
//		if (pair.getValue().entrySet().size()!=10){
//		System.out.println("error 3");
//			return Response.status(Status.BAD_REQUEST).build();
//		}
//		if (!pair.getValue().keySet().containsAll(fields)){
//			System.out.println("error 4");
//			return Response.status(Status.BAD_REQUEST).build();
		//}
		try{		
			map.put(pair.getKey(), value);
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
		
		FineGrainedPair pair = new FineGrainedPair();
		    pair.setKey(key);
		    Map<String, String> value = new HashMap<String, String>();
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
		    pair.setField0(field0);
			pair.setField1(field1);
			pair.setField2(field2);
			pair.setField3(field3);
			pair.setField4(field4);
			pair.setField5(field5);
			pair.setField6(field6);
			pair.setField7(field7);
			pair.setField8(field8);
			pair.setField9(field9);
		    table = tableName;
		
		if (table==null||!table.equals(tableName)){
		    //System.out.println("error 1");
			return Response.status(Status.BAD_REQUEST).build();
		}
		if (!map.containsKey(pair.getKey())){
			//System.out.println("error 2");
			return Response.status(Status.NOT_MODIFIED).build();
		}
//		if (pair.getValue().entrySet().size()!=10){
//			//System.out.println("error 3");
//			return Response.status(Status.BAD_REQUEST).build();
//		}
//		if (!pair.getValue().keySet().containsAll(fields)){
//			//System.out.println("error 4");
//			return Response.status(Status.BAD_REQUEST).build();
//		}
		try{		
			map.put(pair.getKey(), value);
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
		response = List.class)
@Timed
public Response getScannedPairs(@QueryParam(value="table") String table, @QueryParam(value="key") String key, @QueryParam(value="length") Integer length) {
		List<FineGrainedPair> pairList = new ArrayList<FineGrainedPair>();
		if (map.containsKey(key)) {
			//Iterator it = map.entrySet.iterator();
			Iterator it = map.object2ObjectEntrySet().iterator();
			boolean foundKey= false;
			Integer counter = 0;
			while (it.hasNext() && counter<length) {
				Map.Entry<String, Map<String, String>> entry = (Entry<String, Map<String, String>>) it.next();
				System.out.println("KEY:"+entry.getKey());
				if (!foundKey) {
					if (entry.getKey().equals(key)) {
						FineGrainedPair tuple = new FineGrainedPair();
						tuple.setKey(key);
		    			    tuple.setField0(entry.getValue().get("field0"));
		    			    tuple.setField1(entry.getValue().get("field1"));
		    			    tuple.setField2(entry.getValue().get("field2"));
		    			    tuple.setField3(entry.getValue().get("field3"));
		    			    tuple.setField4(entry.getValue().get("field4"));
		    			    tuple.setField5(entry.getValue().get("field5"));
		    			    tuple.setField6(entry.getValue().get("field6"));
		    			    tuple.setField7(entry.getValue().get("field7"));
		    			    tuple.setField8(entry.getValue().get("field8"));
		    			    tuple.setField9(entry.getValue().get("field9"));
						pairList.add(tuple);
						foundKey=true;
						counter++;
					}
				}
				else{
					if (counter<length) {
						FineGrainedPair tuple = new FineGrainedPair();
						tuple.setKey(entry.getKey());
						tuple.setField0(entry.getValue().get("field0"));
		    			    tuple.setField1(entry.getValue().get("field1"));
		    			    tuple.setField2(entry.getValue().get("field2"));
		    			    tuple.setField3(entry.getValue().get("field3"));
		    			    tuple.setField4(entry.getValue().get("field4"));
		    			    tuple.setField5(entry.getValue().get("field5"));
		    			    tuple.setField6(entry.getValue().get("field6"));
		    			    tuple.setField7(entry.getValue().get("field7"));
		    			    tuple.setField8(entry.getValue().get("field8"));
		    			    tuple.setField9(entry.getValue().get("field9"));
						pairList.add(tuple);
						counter++;
					}
				}	
			}
			
		}
		//FineGrainedPairs result = new FineGrainedPairs(pairList);
		return Response.ok(pairList).build();          
	}
}
	
		