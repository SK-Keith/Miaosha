package com.keith.miaosha.page;

//import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author YMX
 * @date 2019/4/14 9:36
 */
public class Page4Navigator<T> {
////    Page<T> pageFromJPA;
//
//    int navigatePages;
//
//    int totalPages;
//
//    int number;
//
//    long totalElements;
//
//    int size;
//
//    int numberOfElements;
//
//    List<T> content;
//
//    boolean isHasContent;
//
//    boolean first;
//
//    boolean last;
//
//    boolean isHasNext;
//
//    int[] navigatepageNums;
//
//    boolean isHasPrevious;
//
//    //空的构造函数用于 Redis从json格式转换为Page4Navigator对象
//    public Page4Navigator(){
//
//    }
//
//    public Page4Navigator(Page<T> pageFromJPA, int navigatePages){
//        this.pageFromJPA = pageFromJPA;
//        this.navigatePages = navigatePages;
//
//        totalPages = pageFromJPA.getTotalPages();
//
//        number  = pageFromJPA.getNumber() ;
//
//        totalElements = pageFromJPA.getTotalElements();
//
//        size = pageFromJPA.getSize();
//
//        numberOfElements = pageFromJPA.getNumberOfElements();
//
//        content = pageFromJPA.getContent();
//
//        isHasContent = pageFromJPA.hasContent();
//
//        first = pageFromJPA.isFirst();
//
//        last = pageFromJPA.isLast();
//
//        isHasNext = pageFromJPA.hasNext();
//
//        isHasPrevious  = pageFromJPA.hasPrevious();
//
//        calcNavigatepageNums();
//    }
//
//    private void calcNavigatepageNums() {
//        int navigatepageNums[];
//        int totalPages = getTotalPages();
//        int num = getNumber();
//
//        //当总页数小于或等于导航页码数时
//        if(totalPages <= navigatePages){
//            navigatepageNums = new int[totalPages];
//            for (int i = 0; i< totalPages;i++){
//                navigatepageNums[i] = i+1;
//            }
//        }else {
//            navigatepageNums = new int[navigatePages];
//            int startNum = num - navigatePages / 2;
//            int endNum = num + navigatePages / 2;
//
//            if(startNum < 1){
//                startNum = 1;
//
//                for (int i=0; i< navigatePages;i++){
//                    navigatepageNums[i] = startNum++;
//                }
//            }else if(endNum > totalPages){
//                endNum = totalPages;
//
//                for (int i = navigatePages - 1; i >= 0;i--){
//                    navigatepageNums[i] = endNum--;
//                }
//            }else{
//
//                for (int i=0;i < navigatePages; i++){
//                    navigatepageNums[i] = startNum++;
//                }
//            }
//        }
//        this.navigatepageNums = navigatepageNums;
//    }
//
//    public int getNavigatePages() {
//        return navigatePages;
//    }
//
//    public void setNavigatePages(int navigatePages) {
//        this.navigatePages = navigatePages;
//    }
//
//    public int getTotalPages() {
//        return totalPages;
//    }
//
//    public void setTotalPages(int totalPages) {
//        this.totalPages = totalPages;
//    }
//
//    public int getNumber() {
//        return number;
//    }
//
//    public void setNumber(int number) {
//        this.number = number;
//    }
//
//    public long getTotalElements() {
//        return totalElements;
//    }
//
//    public void setTotalElements(long totalElements) {
//        this.totalElements = totalElements;
//    }
//
//    public int getSize() {
//        return size;
//    }
//
//    public void setSize(int size) {
//        this.size = size;
//    }
//
//    public int getNumberOfElements() {
//        return numberOfElements;
//    }
//
//    public void setNumberOfElements(int numberOfElements) {
//        this.numberOfElements = numberOfElements;
//    }
//
//    public List<T> getContent() {
//        return content;
//    }
//
//    public void setContent(List<T> content) {
//        this.content = content;
//    }
//
//    public boolean isHasContent() {
//        return isHasContent;
//    }
//
//    public void setHasContent(boolean hasContent) {
//        isHasContent = hasContent;
//    }
//
//    public boolean isFirst() {
//        return first;
//    }
//
//    public void setFirst(boolean first) {
//        this.first = first;
//    }
//
//    public boolean isLast() {
//        return last;
//    }
//
//    public void setLast(boolean last) {
//        this.last = last;
//    }
//
//    public boolean isHasNext() {
//        return isHasNext;
//    }
//
//    public void setHasNext(boolean hasNext) {
//        isHasNext = hasNext;
//    }
//
//    public int[] getNavigatepageNums() {
//        return navigatepageNums;
//    }
//
//    public void setNavigatepageNums(int[] navigatepageNums) {
//        this.navigatepageNums = navigatepageNums;
//    }
//
//    public boolean isHasPrevious() {
//        return isHasPrevious;
//    }
//
//    public void setHasPrevious(boolean hasPrevious) {
//        isHasPrevious = hasPrevious;
//    }
}
