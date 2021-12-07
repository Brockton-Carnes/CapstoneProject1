package application;


/**
 * Purpose of this class is to combine different time segments together
 * @author brock
 *
 */
public class TimeSegmant {
	
	private String time;
	private String content;
	
	TimeSegmant(String time, String content){
		this.time = time;
		this.content = content;
		
	}
	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public TimeSegmant() {
		this.time = "";
		this.content = "";
	}

	/**
	 * The key logger takes in lines of a certain amount of characters 
	 * Checks different time segments, combines all same time frames
	 * @param times
	 * @throws IllegalArgumentException
	 */
	public void concat(TimeSegmant times) throws IllegalArgumentException{
 
		if(!times.time.equals(this.time)) throw new IllegalArgumentException("Error, cant combine time segmants with different time stamps("
		+ times.time + "," + this.time + ")");
		
		this.content += times.content;
	}
	
	public String printSegment(){return this.time + "\n\t" + this.content + "\n";}

	@Override
	public String toString() {
		return "TimeSegmant [time=" + time + ", content=" + content + "]";
	}
	
}
