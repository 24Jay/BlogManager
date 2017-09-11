package com.jay.blog;

public class Response<RESULT>
{

	public static final String _404 = "Resource Not Exists!";

	public static final String _200 = "Operation Succeed!";

	public static final String _501 = "Operation Failed!";
	
	public static final String _401 = "Unauthorized!";

	private int status;

	private String message;

	private RESULT result;

	public Response(int status, String message)
	{
		this(status, message, null);
	}

	public Response(int status, String message, RESULT result)
	{
		this.status = status;
		this.message = message;
		this.result = result;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public RESULT getResult()
	{
		return result;
	}

	public void setResult(RESULT result)
	{
		this.result = result;
	}

	

}
