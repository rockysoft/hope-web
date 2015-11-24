package com.github.rockysoft.entity;


/**
 * @author Administrator
 *
 */
public class ServerInfo {
	
	private String initMemory;
	private String maxMemory;
	private String usedMemory;
	private String osName;
	private String osVersion;
	private String vmName;
	private String osArch;
	private int osAvailableProcessors;
	private double osSystemLoadAverage;
//	private String CompilationName;

	public String getInitMemory() {
		return initMemory;
	}
	public void setInitMemory(String initMemory) {
		this.initMemory = initMemory;
	}
	public String getMaxMemory() {
		return maxMemory;
	}
	public void setMaxMemory(String maxMemory) {
		this.maxMemory = maxMemory;
	}
	public String getUsedMemory() {
		return usedMemory;
	}
	public void setUsedMemory(String usedMemory) {
		this.usedMemory = usedMemory;
	}
	public String getOsName() {
		return osName;
	}
	public void setOsName(String osName) {
		this.osName = osName;
	}
	public String getOsVersion() {
		return osVersion;
	}
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
	public String getVmName() {
		return vmName;
	}
	public void setVmName(String vmName) {
		this.vmName = vmName;
	}
	public String getOsArch() {
		return osArch;
	}
	public void setOsArch(String osArch) {
		this.osArch = osArch;
	}
	public int getOsAvailableProcessors() {
		return osAvailableProcessors;
	}
	public void setOsAvailableProcessors(int osAvailableProcessors) {
		this.osAvailableProcessors = osAvailableProcessors;
	}
	public double getOsSystemLoadAverage() {
		return osSystemLoadAverage;
	}
	public void setOsSystemLoadAverage(double osSystemLoadAverage) {
		this.osSystemLoadAverage = osSystemLoadAverage;
	}
//	public String getCompilationName() {
//		return CompilationName;
//	}
//	public void setCompilationName(String compilationName) {
//		CompilationName = compilationName;
//	}
	
}
