package mx.cicese.dcc.teikoku.scheduler.grid.strategy.rigid;

import java.util.List;
import java.util.UUID;

import de.irf.it.rmg.core.teikoku.job.Job;
import de.irf.it.rmg.core.teikoku.workload.job.SWFJob;
import mx.cicese.dcc.teikoku.scheduler.plan.AllocationEntry;
import mx.cicese.dcc.teikoku.scheduler.plan.JobControlBlock;

/**
 * (non-Javadoc)
 * 
 * @see mx.cicese.dcc.teikoku.scheduler.grid.strategy.rigid.RigidStgy
 *
 * @author <a href="mailto:ahirales@cicese.edu.mx">Adan Hirales Carbajal</a>
 *         (last modified by: $Author$)
 * @version $Version$, $Date$
 * 
 * @category Rigid Scheduling Strategy
 */
public class Rand extends RigidStgy{ 
	/**
	 * Class constructor
	 * 
	*/
		public Rand(){
			super();
		}
		
	/**
	 * (non-Javadoc)
	 * 
	 * @see mx.cicese.dcc.teikoku.scheduler.grid.strategy.rigid.RigidStgy#schedule(Job job)
	 */
	public AllocationEntry schedule(Job job, JobControlBlock jcb) {
		int numMachines  = 0, i = 0;
		UUID targetMachine = null;
		
		
		// GET THE LIST OF MACHINES IN THE SYSTEM
		if(jobFilteringStgy.getKnownMachines().size() == 0){
			jobFilteringStgy.setKnownMachines(gridInfBroker.getKnownMachines(), gridInfBroker);
		}
		
		List<UUID> machineSet = jobFilteringStgy.getKnowMachines();
			
		// SELECT A TARGET MACHINE
		numMachines = machineSet.size();
		i = (int) Math.round((Math.random()*(numMachines-1)));
		targetMachine = machineSet.get(i);
			
		// CREATE AN ALLOCATION ENTRY
		AllocationEntry entry = new AllocationEntry((SWFJob)job, targetMachine, -1);
			
		return entry;
	} 
}
