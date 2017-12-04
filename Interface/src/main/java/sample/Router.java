package sample;

public class Router {
	private  double processingDelay;
	private String routerID;

	public Router(int i,double processingTime)
	{
		this.processingDelay = processingTime;
		this.routerID = i +"";
	}

	public void setRouterID(String routerID)
	{
		this.routerID = routerID;
	}

	public String getRouterID()
    {
        return routerID;
    }

	public double getProcessingDelay()
	{
		return this.processingDelay;
	}

	public void changeProcessingDelay(double processingTime)
	{
		this.processingDelay = processingTime;
	}

}
