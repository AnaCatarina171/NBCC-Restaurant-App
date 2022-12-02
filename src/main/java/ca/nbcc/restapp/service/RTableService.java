package ca.nbcc.restapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.nbcc.restapp.model.RTable;
import ca.nbcc.restapp.repo.RTableJpaRepo;

@Service
public class RTableService {

		private RTableJpaRepo tR;
		
		@Autowired
		public RTableService(RTableJpaRepo tR) {
			super();
			this.tR = tR;
		}
		
		public RTable findRTableById(Long tMID_LONG) throws Exception{
			
			if(tR.findById((long)tMID_LONG).isPresent()) {
				
				return tR.findById((long)tMID_LONG).get();
			}
			else if(tR.findById((long)tMID_LONG).isEmpty()) {
				
				throw new Exception("RTable not found: ID " + tMID_LONG);
			}
			
			return null;
		}

		public List<RTable> getAllRTable(){
			return tR.findAll();
		}
		
		public RTable addNewRTable(RTable t) {
			return tR.save(t);
		}

		public RTable updateRTable(RTable t) {
			return tR.save(t);
		}

		public void deleteRTable(Long tMID_LONG) {
				
			tR.deleteById(tMID_LONG);
		}

		public RTable findRTableByNumber(Long tNumber) {

			return tR.findByNumber(tNumber);
		}
}
