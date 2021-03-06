package State;

import Exceptions.PlanEntryStateNotMatchException;
import common.CommonPlanningEntry;

public class WaitingState implements EntryState{
	@Override
	public String getStateName() {
		return "Waiting";
	}
	
	/**
	 * 非法状态转换
	 * @throws PlanEntryStateNotMatchException 非法状态转换
	 */
	@Override
	public <R> void start(CommonPlanningEntry<R> cpe) throws PlanEntryStateNotMatchException {
		throw new PlanEntryStateNotMatchException(getStateName());
	}
	
	/**
	 * 设置状态为分配状态
	 */
	@Override
	public <R> void allocate(CommonPlanningEntry<R> cpe) {
		cpe.setState(new AllocatedState());
	}
	
	/**
	 * 非法状态转换
	 * @throws PlanEntryStateNotMatchException 非法状态转换
	 */
	@Override
	public <R> void end(CommonPlanningEntry<R> cpe) throws PlanEntryStateNotMatchException {
		throw new PlanEntryStateNotMatchException(getStateName());
	}
	
	/**
	 * 设置状态为取消状态
	 */
	@Override
	public <R> void cancel(CommonPlanningEntry<R> cpe) {
		cpe.setState(new CancelledState());
	}
}
