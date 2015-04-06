/*
Author: Rahul Bhonsale
Description: Simulate the optimal(clairvoyance) paging algorithm for an OS.
Date: 04/05/2015
File: optimalALgo.java
*/
import java.lang.Math;
import java.util.Random;

public class optimal
{
    // Reference string generation
    public static int[] refStrGen(int size)
    {
        int[] refStr = new int[size];
        
        for(int i=0;i<size;i++)
        {
            refStr[i] = -1;
        }
        
        
        int[] ninetySet = {1,2,3,4,5,6,7,8,9};
        
        for( int i=0; i<size;i++)
        {
            int num = 0;
            double progNum = Math.random();
            
            
            if(progNum < .9)
            {

                int chk = 0;
                while( chk == 0)
                {
                    int rnd = new Random().nextInt(ninetySet.length);
                    num = ninetySet[rnd];
                    if( i == 0 )
                    {
                        refStr[i] = num;
                        chk = 1;
                    }
                    else if(i!=0 && refStr[i-1] != num)
                    {
                        refStr[i] = num;
                        chk =1;
                    }
                    
                }
                 ;
            }
            else
            {
                int chk = 0;
                while( chk == 0 )
                {
                    double tmp = Math.random() * 100.0;
                    num = (int) tmp;
                    
                    if( i == 0 )
                    {
                        refStr[i] = num;
                        chk = 1;
                    }
                    else if(refStr[i-1] != num)
                    {
                        refStr[i] = num;
                        chk = 1;
                    }
                }
                
            }
        }
        
        return refStr;
        
    }
    
    // Contains method
    public static boolean contains(int[] arr, int key)
    {
        for(int i=0;i<arr.length;i++)
        {
            if(arr[i] == key)
            {
                return true;
            }
            
        }
        
        return false;
    }
    
    // Returns a copy of array from given position to end.
    public static int[] subArray(int[] arr, int pos)
    {
        int size = arr.length - pos;
        int temp[] = new int[size];
        for(int i=pos;i<size-1;i++)
        {
            for(int k=0;k<size-1;k++)
            {
                temp[k] = arr[i];
            }
        }
        
        return temp;
    }
    
    public static void optimal(int[] refStr, int resSize)
    {
    	
        int[] resSet = new int[resSize];
        
        // Fill resident set with -1
        for(int i=0;i<resSize;i++)
        {
            resSet[i] = -1;
        }
        // Track current page while initially filling resident set;
        int curpage  = 0;
        
        int faults   = 0;
        
        // Iterate through ref String and keep track of position
        for(int i=0; i<refStr.length;i++)
        {
            boolean cont = contains(resSet, refStr[i]);
            
            // Iterate through resident set and keep track of current loading page.
            for(int k=curpage;k<refStr.length;k++)
            {
                //System.out.print("Page to load: "  + refStr[i] + " " );
                // If res set does not contain the current page to load and res Set is not full.
                if ( !cont && curpage < resSize)
                {
                    resSet[curpage] = refStr[i];
                    curpage++;
                    faults++;
                    break;
                }
                // If res set does not contain page to load and res set is full. Optimal call.
                else if( !cont || !cont && curpage > resSize )
                {
                    int refPos     = i;
                    int pageToLoad = refStr[i];
                    // find the page to replace in res set using optimal
                    int pageToReplace  = opt(resSet, refStr, refPos);
                    resSet[pageToReplace] = pageToLoad; 
                    faults++;
                    break;
                }
                // If the page is already in the resident set.
                else if( cont )
                {
                    continue;
                }
                // Move on to next page in ref String.
                break;
            }
        }
        
        System.out.print("\nRes Set: ");
        for( int x : resSet)
        {
            System.out.print(x + " ");
        }
        System.out.println();
        System.out.println(faults);
    }
    
    // Actual implementation of optimal algorithm.
    // Returns the page number index in resident set to replace.
    public static int opt(int[] resSet, int[] refStr, int pos)
    {
        int max = 0;
        int ret = 0;
        int[] dur = new int[resSet.length];
        
        // Iterate through resident set.
        for(int i=0;i<resSet.length;i++)
        {
            //System.out.print("Rs Cur: " + resSet[i] + " " );
            // And through the remaining ref string.
            for(int k=pos;k<refStr.length;k++)
            {
                //System.out.print("Rf Cur: " + refStr[k] + " " );
                
                // When next occurrence of page in res set,
                // 	Stop and store distance in a temporary duration array.
                if(resSet[i] == refStr[k])
                {
                    dur[i] = k;
                    break;
                }
            } 
            
        }
        
        // Check to see which index of the resident set has the highest distance
        // 	If a page in the resident set never shows, return that index because
        //		that page will never be used again.
        for( int i=0;i<dur.length;i++)
        {
            
            int temp = dur[i];
            // System.out.print("DUR ARR: " + temp + " " );
            if(temp == 0)
            {
                ret = i;
                return ret;
            }
            else if(max<temp)
            {
                max = temp;
                ret = i;
            }
        }
        // System.out.println();
        
        return ret;
    }

    public static void main(String[] args)
    {
        int[] test = refStrGen(50);
        
        
        System.out.println();
        
        optimal(test,5);
    }
    
}