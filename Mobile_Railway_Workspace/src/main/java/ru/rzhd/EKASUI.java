package ru.rzhd;

public class  EKASUI{
	private int roadCode;
	private int regionCode;
	private String areaCode;
	private String stationStartName;
	private String stationFinishName;
	private int objectCode;
	private boolean resultState;
	
	
	//Constructor for EKASUI Class ↓
	public EKASUI(int roadCode,int regionCode,String areaCode,String stationStartName,String stationFinishName,int objectCode,boolean resultState) {
		this.roadCode=roadCode;
		this.regionCode=regionCode;
		this.areaCode=areaCode;
		this.stationStartName=stationStartName;
		this.stationFinishName=stationFinishName;
		this.objectCode=objectCode;
		this.resultState=resultState;
	}
	
	//Here are setters ↓
	public int getRoadCode() {
		return roadCode;
	}
	public int getRegionCode() {
		return regionCode;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public String getStationStartName() {
		return stationStartName;
	}
	public String getStationFinishName() {
		return stationFinishName;
	}
	public int getObjectCode() {
		return objectCode;
	}
	public boolean isResultState() {
		return resultState;
	}
	public void setRoadCode(int roadCode) {
		this.roadCode = roadCode;
	}
	public void setRegionCode(int regionCode) {
		this.regionCode = regionCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public void setStationStartName(String stationStartName) {
		this.stationStartName = stationStartName;
	}
	public void setStationFinishName(String stationFinishName) {
		this.stationFinishName = stationFinishName;
	}
	public void setObjectCode(int objectCode) {
		this.objectCode = objectCode;
	}
	public void setResultState(boolean resultState) {
		this.resultState = resultState;
	}
	
	
	
	
}
