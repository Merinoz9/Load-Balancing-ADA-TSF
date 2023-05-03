package mx.cicese.dcc.teikoku.scheduler.grid.strategy.rigid;

import java.util.List;
import java.util.UUID;

import de.irf.it.rmg.core.teikoku.job.Job;
import de.irf.it.rmg.core.teikoku.workload.job.SWFJob;
import mx.cicese.dcc.teikoku.scheduler.plan.AllocationEntry;
import mx.cicese.dcc.teikoku.scheduler.plan.JobControlBlock;

public class LP extends RigidStgy{
	
	/**
	 * Class constructor
	 * 
	*/
		public LP(){
			super();
		}

		
		/**
		 * (non-Javadoc)
		 * 
		 * @see mx.cicese.dcc.teikoku.scheduler.grid.strategy.rigid.RigidStgy#schedule(Job job)
		 */
		public AllocationEntry schedule(Job job, JobControlBlock jcb) {
			
			// GET THE LIST OF MACHINES IN THE SYSTEM
			if(jobFilteringStgy.getKnownMachines().size() == 0){
				jobFilteringStgy.setKnownMachines(gridInfBroker.getKnownMachines(), gridInfBroker);
			}
			
			List<UUID> machineSet = jobFilteringStgy.getKnowMachines();
			
			// Get the job paramters
			String id = job.getName() + job.getReleaseTime().toString() + job.getPriority();
			
			// CREATE AN ALLOCATION ENTRY
			AllocationEntry entry = null;
				
			return entry;
		} 
}
