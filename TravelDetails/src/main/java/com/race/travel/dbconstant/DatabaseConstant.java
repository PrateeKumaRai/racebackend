package com.race.travel.dbconstant;

public class DatabaseConstant {
	private DatabaseConstant() {

	}
	
	public static final String DATE_AND_LOCATION = "SELECT * FROM travel_details td WHERE (td.start_date >= ?1 AND td.start_date<= ?2) AND (td.src_location=?3 AND td.dest_location=?4) AND (td.src_country=?5 AND td.dest_country=?6)";
	public static final String FIND_BY_EMPLOYEEID ="SELECT * FROM travel_details td WHERE td.emp_id = ?1 ";
	public static final String FIND_BY_EMPLOYEEID_AND_TRAVELID= "SELECT * FROM travel_details td WHERE td.emp_id = ?1 AND td.travel_id = ?2";
	public static final String FIND_BY_DATE= "SELECT * FROM travel_details td WHERE (td.start_date >= ?1 AND td.start_date<= ?2)";
	public static final String FIND_BY_LOCATION="SELECT * FROM travel_details td WHERE (td.src_location = ?1 AND td.dest_location= ?2) AND (td.src_country=?3 AND td.dest_country=?4)";
}
