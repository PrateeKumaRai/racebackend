package com.race.constant;

/**
 * @author praterai
 *
 */
public class QueryConstant {

	public static final String FIND_CUBICAL_BY_STATUS = "select * from cubical_tab where cubical_status=?1 and ?2 between start_date and return_date";

	public static final String UPDATE_OWNER_CUBICAL = "update cubical_tab  set cubical_status = ?1 where email_id = ?2 ";

	public static final String UPDATE_TEMP_CUBICAL = "update cubical_tab  set cubical_number_temp = ?1 ,seat_mail_id= ?2 ,seat_number_temp= ?3 where email_id = ?4 ";

	public static final String UPDATE_SEAT_STATUS = "update cubical_tab  set cubical_status = ?1, start_date = ?2, return_date= ?3 where email_id = ?4 ";

	public static final String FIND_BY_MAIL = "SELECT * FROM cubical_tab where email_id=?1";

	public static final String RESET_CUBICAL = "update cubical_tab  set cubical_status= ?1 where  ?2 between start_date and return_date";

	public static final String RESET_TEMP_BOOKING = "update cubical_tab  set cubical_number_temp = ?1 where cubical_number_temp = ?2 ";

	public static final String UNBOOK_CUBICAL = "update cubical_tab  set seat_mail_id = ?1 ,seat_number_temp = ?2 ,cubical_number_temp= ?3  where email_id=?4 ";

	public static final String FILTER_CUBICAL = "select * from cubical_tab where country = ?1 and city = ?2 and cubical_location = ?3 and cubical_status = ?4  and ?5 between start_date and return_date";

}
