package ca.nbcc.restapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class PaginationService <T> {
	public List<Pageable> generatePageableList(int totalItems, int itemPerPage){
		List<Pageable> pageableList = new ArrayList<>();
		int totalPages = 0;
		
		if(totalItems/itemPerPage > (int)(totalItems / itemPerPage) ) {
			totalPages = (int) totalItems / itemPerPage + 1;
		}else {
			totalPages = (int) totalItems / itemPerPage;
		}
		
		
		for (int i = 0; i <= totalPages ; i++) {
			pageableList.add(PageRequest.of(i ,itemPerPage));			
//			if(i == totalPages-1) {
//				int n = totalItems - (itemPerPage * i); // how many left 
//				pageableList.add(PageRequest.of(0,n));
//			}else {
//				
//			}			
		}
		//System.out.println(pageableList.toString());
		return pageableList;
	}
	
	public List<Integer> generatePagesList(){
		List<Integer> pageList = new ArrayList<>();
		pageList.add(1);
		return pageList;
	}
	
	public List<Integer> generatePagesList(Page<T> pages){
		List<Integer> pageList = new ArrayList<>();
		for (int i = 0; i < pages.getTotalPages(); i++) {
			pageList.add(i+1);
		}
		return pageList;
	}
}
