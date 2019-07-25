package com.organization.organizationDetails.ServiceImpl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.organization.organizationDetails.builder.AllocationResponseBuilder;
import com.organization.organizationDetails.builder.EmployeeResponseBuilder;
import com.organization.organizationDetails.builder.ProjectResponseBuilder;
import com.organization.organizationDetails.exception.AllocationCreationException;
import com.organization.organizationDetails.exception.AllocationISNotFoundException;
import com.organization.organizationDetails.exception.AllocationNotFoundException;
import com.organization.organizationDetails.jsonAPI.Data;
import com.organization.organizationDetails.jsonAPI.MultiDataResponse;
import com.organization.organizationDetails.jsonAPI.SingleDataResponse;
import com.organization.organizationDetails.jsonAPI.Warnings;
import com.organization.organizationDetails.jsonAPIErrors.Errors;
import com.organization.organizationDetails.model.Allocation;
import com.organization.organizationDetails.model.AllocationID;
import com.organization.organizationDetails.model.Employee;
import com.organization.organizationDetails.model.Project;
import com.organization.organizationDetails.repository.AllocationRepository;
import com.organization.organizationDetails.repository.EmployeeRepository;
import com.organization.organizationDetails.repository.ProjectRepository;
import com.organization.organizationDetails.service.AllocationService;
import com.organization.organizationDetails.service.RelationService;

@Service
public class AllocationServiceImpl<T> implements AllocationService {

	@Autowired
	private AllocationRepository allocationRepository;
	

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private AllocationResponseBuilder<T> allocationResponseBuilder;
	
	private List<Data> dataList;
	
	private Data data;
	
	private List<Warnings> warnings;

	@Override
	public SingleDataResponse<Allocation> createAllocation(Allocation allocation) throws AllocationCreationException
	{
		/*if(!(allocation.getStartDate().after(projectRepository.findByWonAndIsDeleted(allocation.getWon(), false).getStartDate()) && 
				allocation.getEndDate().before(projectRepository.findByWonAndIsDeleted(allocation.getWon(), false).getEndDate())))*/
		if(!(allocation.getStartDate().compareTo(projectRepository.findByWonAndIsDeleted(allocation.getWon(), false).getStartDate())>=0)
				&& allocation.getEndDate().compareTo(projectRepository.findByWonAndIsDeleted(allocation.getWon(), false).getEndDate())<=0)
		throw new AllocationCreationException("Allocation can't be created.",allocation.getWon());
		allocationRepository.save(allocation);
		return new SingleDataResponse<>(new ArrayList<Warnings>(),allocationResponseBuilder.transformAllocation(allocation),new ArrayList<Errors>());
	}

	@SuppressWarnings("unchecked")
	@Override
	public MultiDataResponse<Allocation> createAllocationOfEmployees(List<Integer> empIDs, int won, Date startDate,
			Date endDate, String role) throws AllocationCreationException
	{
		if(!((startDate.after(projectRepository.findByWonAndIsDeleted(won, false).getStartDate())|| startDate.equals(projectRepository.findByWonAndIsDeleted(won, false).getStartDate())) && 
				(endDate.before(projectRepository.findByWonAndIsDeleted(won, false).getEndDate()) || endDate.equals(projectRepository.findByWonAndIsDeleted(won, false).getEndDate()))))
			throw new AllocationCreationException("Allocation can't be created",won);
		dataList = new ArrayList<>();
		empIDs.stream().map(empID -> new Allocation(empID, won, startDate, endDate, role, false))
		.map(allocation -> allocationResponseBuilder.transformAllocation(allocation))
		.forEach(data -> dataList.add(data));
		
		
		/*empIDs.stream().forEach(empID -> {
			dataList.add(allocationResponseBuilder.transformAllocation(new Allocation(empID, won, startDate, endDate, role, false)));
		});*/
		return new MultiDataResponse<Allocation>(new ArrayList<Warnings>(),dataList,new ArrayList<Errors>());
//		empIDs
//		.stream()
//		.map(x -> new Allocation(x, won, startDate, endDate, role, false))
//		.map(x -> new ArrayList<>(Arrays.asList(x)))
//		.reduce(new ArrayList(), (x, y) -> {x.addAll(y);return x;});
		
		/*return new MultiDataResponse<Allocation>(
						empIDs
						.stream()
						.map(x -> new Allocation(x, won, startDate, endDate, role, false))
						.map(x-> new AllocationResponseBuilder<T>().transformAllocation(x))
						.map(x -> {List<Data> d=new ArrayList<>();d.add(x);return d;})
						.reduce(new ArrayList<>(), (x, y) -> {x.addAll(y);return x;})
				, errors);*/
		//return new MultiDataResponse<Allocation>(allocationResponseBuilder.transformAllocations(allocations),errors);
	}
	
	
	@Override
	public SingleDataResponse<Allocation> getCurrentAllocation(int empID) throws AllocationISNotFoundException {
		if(allocationRepository.findByEmpID(empID).isEmpty() 
				|| allocationRepository.findByEmpID(empID).stream().filter(allocation -> allocation.isDeleted()).count()>0)
			throw new AllocationISNotFoundException("Employee currently not allocated");
		allocationRepository.findByEmpID(empID).stream()
		.filter(allocation -> (new Date().compareTo(allocation.getStartDate())>=0 && new Date().compareTo(allocation.getEndDate())<=0))
		.forEach(allocation ->
		data=allocationResponseBuilder.transformAllocation(allocation));
		return new SingleDataResponse<>(new ArrayList<Warnings>(),data,new ArrayList<Errors>());
	}
	
	@Override
	public MultiDataResponse<Allocation> getFutureAllocations(int won,int empID) throws AllocationNotFoundException
	{
		//List<Allocation> allocations=allocationRepository.findByWonAndEmpID(won,empID);
		dataList=new ArrayList<>();
		if(allocationRepository.findByWonAndEmpID(won,empID).isEmpty())
			throw new AllocationNotFoundException("No such allocation found");
		allocationRepository.findByWonAndEmpID(won,empID).stream().filter(allocation -> 
				//((new Date().after(allocation.getStartDate()) && new Date().before(allocation.getEndDate())) ||
				(allocation.getStartDate().compareTo(new Date())>=0 && allocation.getEndDate().after(new Date())))
				.sorted(Comparator.comparing(Allocation::getStartDate))
				.map(allocation -> allocationResponseBuilder.transformAllocation(allocation))
				.forEach(data -> dataList.add(data));
		
				/*.forEach(allocation ->
				dataList.add(allocationResponseBuilder.transformAllocation(allocation)))*/
				;
		//allocations.stream().filter(allocation -> allocation.getStartDate().compareTo(new Date()>=0));
		 return new MultiDataResponse<>(new ArrayList<Warnings>(),dataList,new ArrayList<Errors>());
		
		
		/*List<Allocation> allocations=allocationRepository.findByWonAndEmpID(won,empID);
		System.out.println("allocations: :"+allocations);
		dataList=new ArrayList<>();
			if(allocations.isEmpty())
			{  
				errors=new ArrayList<>();
				errors.add(new Errors(empID, HttpStatus.NOT_FOUND , "Employee not allocated", "Employee is never allocated in this won"));
				return new MultiDataResponse<>(dataList,errors);
			}
			else
			if(allocations.stream().filter(allocation -> allocation.isDeleted()).count()>0)
			{
				errors=new ArrayList<>();
				errors.add(new Errors(empID, HttpStatus.BAD_REQUEST , "Employee no more allocated", "Removed from project"));
				
				allocations.stream().forEach(allocation ->
				dataList.add(allocationResponseBuilder.transformAllocation(allocation)));
				return new MultiDataResponse<>(dataList,errors);
			}
			errors=new ArrayList<>();
			System.out.println("allocations:2 :"+allocations);
			allocations.stream().filter(allocation -> !(allocation.isDeleted())).forEach(allocation ->
			dataList.add(allocationResponseBuilder.transformAllocation(allocation)));
			return new MultiDataResponse<>(dataList,errors);	*/
		}

	@Override
	public MultiDataResponse<Allocation> getAllocationHistory(int empID) throws AllocationNotFoundException
	{
		//List<Allocation> allocations=allocationRepository.findByEmpID(empID);
		dataList=new ArrayList<>();
		if(allocationRepository.findByEmpID(empID).isEmpty())
			throw new AllocationNotFoundException("No such allocation found");
		allocationRepository.findByEmpID(empID).stream().filter(allocation -> 
				(new Date().after(allocation.getStartDate()) && 
						//(new Date().before(allocation.getEndDate()))||
						 (new Date().compareTo(allocation.getEndDate())>=0)))
				.sorted(Comparator.comparing(Allocation:: getStartDate))
				.map(allocation -> allocationResponseBuilder.transformAllocation(allocation))
				.forEach(data -> dataList.add(data));
				
				/*.forEach(allocation ->
				dataList.add(allocationResponseBuilder.transformAllocation(allocation)))
				;*/
		 return new MultiDataResponse<>(new ArrayList<Warnings>(),dataList,new ArrayList<Errors>());
	
//		List<Allocation> allocations=allocationRepository.findByEmpID(empID);
//			errors=new ArrayList<>();
//			if(allocations.isEmpty())
//			{  
//				errors.add(new Errors(empID, HttpStatus.BAD_REQUEST , "Employee not allocated", "Employee is not allocated in any won"));
//				return new MultiDataResponse<>(dataList,errors);
//			}
//			else
//			if(allocations.stream().filter(allocation -> allocation.isDeleted()).count()>0)
//			{
//				errors.add(new Errors(empID, HttpStatus.BAD_REQUEST , "Employee no more allocated", "Removed from project"));
//			}
//			if(!(allocations.isEmpty()) && (allocations.stream().filter(allocation -> allocation.isDeleted())))
//			errors.add(new Errors(empID, HttpStatus.BAD_REQUEST , "Employee Left the job", "Is an Ex-employee"));
//			
//			return new SingleDataResponse<>(employeeResponseBuilder.transformEmployee(employee.get()),errors);
//			

		}
	
	@Override
	public SingleDataResponse<Allocation> updateAllocation(int empID,Allocation allocation) throws AllocationISNotFoundException 
	{
		warnings=new ArrayList<>();
		//List<Allocation> allocations=allocationRepository.findByWonAndEmpID(allocation.getWon(),empID);
		if(allocationRepository.findByWonAndEmpID(allocation.getWon(),empID).isEmpty())
		throw new AllocationISNotFoundException("Allocation not found.Create new allocation");
		if(allocationRepository.findByWonAndEmpID(allocation.getWon(),empID).stream().filter(alloc -> (alloc.getStartDate().equals(allocation.getStartDate()) 
				&& alloc.getEndDate().equals(allocation.getEndDate())))
		        .count()>0)
			 {
				warnings.add(new Warnings("Allocation details are already present"));
				return new SingleDataResponse<>(warnings,data,new ArrayList<Errors>());
			}
		allocationRepository.save(allocation);
		return new SingleDataResponse<>(new ArrayList<Warnings>(),allocationResponseBuilder.transformAllocation(allocation),new ArrayList<Errors>());
	}

	@Override
	public MultiDataResponse<Allocation> updateAllocations(List<Integer> empIDs,int won,Date startDate,Date endDate, String role) throws AllocationNotFoundException {
		dataList = new ArrayList<>();
		if(empIDs.stream().map(empID -> allocationRepository.findByWonAndEmpID(won,empID)).count()==0)
		throw new AllocationNotFoundException("Allocation not found.Create new allocation");
		if(empIDs.stream().map(empID -> allocationRepository.findByWonAndEmpID(won,empID))
				.map(allocations -> new Allocation())
			.filter(alloc -> (alloc.getStartDate().equals(startDate) 
					&& alloc.getEndDate().equals(endDate))).count()>0)
			{
				warnings.add(new Warnings("Allocation details are already present"));
				return new MultiDataResponse<>(new ArrayList<Warnings>(),new ArrayList<Data>(),new ArrayList<Errors>());
			}
		empIDs.stream().map(empID -> allocationRepository.save(new Allocation(empID,won,startDate,endDate,role,false)))
		.map(allocation -> allocationResponseBuilder.transformAllocation(allocation))
		.forEach(data -> dataList.add(data));
		return new MultiDataResponse<Allocation>(new ArrayList<Warnings>(),dataList,new ArrayList<Errors>());
		
		
//		forEach(empID -> {
//			dataList.add(allocationResponseBuilder.transformAllocation(new Allocation(empID, allocation.getWon(), startDate, endDate, role, false)));
//		});
		
	
	}

	@Override
	public SingleDataResponse<Allocation> cancelAllocation(int won, int empID) throws AllocationISNotFoundException
	{
		//List<Allocation> allocations=allocationRepository.findByWonAndEmpID(won,empID);
		if(allocationRepository.findByWonAndEmpID(won,empID).isEmpty())
			throw new AllocationISNotFoundException("Allocation not found");
		/*{  
			errors.add(new Errors(empID, HttpStatus.BAD_REQUEST , "Employee not allocated", "Employee is never allocated in this won"));
			return new SingleDataResponse<>(warnings,new Data<>(),errors);
		}*/
		data=new Data();
		allocationRepository.findByWonAndEmpID(won,empID).stream().filter(allocation -> 
		(new Date().compareTo(allocation.getStartDate())>=0 && new Date().compareTo(allocation.getEndDate())<=0))
		/*((new Date().after(allocation.getStartDate()) && 
				(new Date().before(allocation.getEndDate())) || (new Date().after(allocation.getEndDate())))))*/
		.sorted(Comparator.comparing(Allocation::getStartDate))
			.forEach(allocation ->{
				allocation.setDeleted(true);
				data=allocationResponseBuilder.transformAllocation(allocationRepository.save(allocation));
			});
		return new SingleDataResponse<>(warnings,data,new ArrayList<Errors>());
		/*allocations.stream().skip(allocations.size()-1).findFirst().get().setDeleted(true);
		return new SingleDataResponse<>(allocationResponseBuilder.transformAllocation(
				allocationRepository.save(allocations.stream().skip(allocations.size()-1).findFirst().get())),errors);	*/
		
		
		
		//System.out.println(allocations.stream().skip(allocations.size()-1).findFirst().get());
		//allocationRepository.save(allocations.stream().skip(allocations.size()-1).findFirst().get());
		//allocationResponseBuilder.transformAllocation(allocationRepository.save(allocations.stream().skip(allocations.size()-1).findFirst().get()));
		/*Optional<Allocation> allocation=allocationRepository.findById(new AllocationID(empID, won));
		if(!allocation.isPresent())
		{
			errors=new ArrayList<>();
			errors.add(new Errors(empID, HttpStatus.BAD_REQUEST , "Employee doesn't exist", "Incorrect employee ID"));
			return new SingleDataResponse<>(new Data<>(),errors) ;
		}
		allocation.get().setDeleted(true);
		allocationRepository.save(allocation.get());*/
		
	}

	
}