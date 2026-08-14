class Solution {
    public int searchInsert(int[] nums, int target) {
        int arr[];
        for(int i =0; i<nums.length;i++){
             if(nums[i]>=target){
                return i;
             }
            

        }
        return nums.length;
        
    }
}