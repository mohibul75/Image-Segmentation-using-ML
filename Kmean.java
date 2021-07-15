package imageSegmentation;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class Kmean {
	
	private int k;
	private BufferedImage image;
	private int [][]cluster;
	int width;
	int height;
	Color []centroids;
	Color []temp;
	private double eps=0.15;
	

	
	public static void main(String[]args) throws IOException {
		String s1="C:\\Users\\Dell\\Downloads\\t2.jpg";
		String s2="C:\\Users\\Dell\\Downloads\\out.jpg";
		
		Kmean obj=new Kmean();
		obj.controller(s1, s2);
		
	}
	
	
	public void controller(String filePath , String output) throws IOException {
		
		System.out.println("Enter the value of K :  ");
		Scanner input =new Scanner(System.in);
		k=input.nextInt();
		
		centroids=new Color[k];
		temp=new Color[k];
		
		
		Random rand = new Random();
		
		for(int i=0 ; i<centroids.length ; i++) {
			
			centroids[i]=new Color(rand.nextInt(255) , rand.nextInt(255) , rand.nextInt(255));
			 System.out.println("	rB	"+centroids[i].getRed()+"	gB	"+centroids[i].getGreen()+"	bB	"+centroids[i].getBlue());
		}
		
		
		
	//	 int count=0;
		 File imageInput = new File(filePath);
		 image = ImageIO.read(imageInput);
		 
		 width = image.getWidth();
         height = image.getHeight();
         
         cluster=new int[width][height];
         int loop=0;
         
         do {
        	 
        	 
        	 for(int i=0; i<height; i++) {
	         
        		 for(int j=0; j<width; j++) {
        			 
        			 double []distanceList = new double[k];
        			 Color c = new Color(image.getRGB(j, i));	
	            	
        			 for(int h=0 ; h<k ; h++) {
	            	
	            		double	distance = Math.sqrt(Math.pow(c.getRed()-centroids[h].getRed() ,2)
	            						 +Math.pow(c.getGreen()-centroids[h].getGreen() ,2)
	            						 +Math.pow(c.getBlue()-centroids[h].getBlue() ,2));
	            		distanceList[h]=distance;	
	            	}
	            	
        			// System.out.println(minValueIndex(distanceList));
	            	cluster[j][i]=minValueIndex(distanceList);
	            	//System.out.println("w	"+j+"	h	"+i+"	cluster	"+cluster[j][i]);
	            }
        	 } 
        		 int g=0;
        		 int r=0;
        		 int b=0;
        		 int num=0;
        		 Color c1;
        		 for(int w=0 ; w<k ; w++) {
        			 
        			 num=1;
        			 r=0;
        			 g=0;
        			 b=0;
        		 
        			 for(int x=0 ; x<height ; x++) {
            			 for(int y=0 ; y<width ; y++) {
            				 
            				 if(cluster[y][x]==w) {
            					 
            					 num++;
            					 c1 = new Color(image.getRGB(y, x));
            					 
            					 g+=c1.getGreen();
            					 r+=c1.getRed();
            					 b+=c1.getBlue();
            				 }
            			 }
            		 }
        			 
        			 System.out.println("iteration no: "+(loop+1)+"	r	"+(int)r/num+"	g	"+(int)g/num+"	b	"+(int)b/num);
        			 temp[w]=new Color((int)r/num , (int)g/num ,(int)b/num);
        		 }
        		 
        		 
        		 for(int u=0 ;u<k ; u++) {

        			 Color t;
        			 t=temp[u];
        			 temp[u]=centroids[u];
        			 centroids[u]=t;
        		 }
        		 
      //   }
        	 loop++;
        	 
        	 System.out.println("eps	"+eps);
        	 
        	 System.out.println(Math.abs(centroids[0].getRed()-temp[0].getRed())+"\n"+Math.abs(centroids[0].getBlue()-temp[0].getBlue())+"\n"
        	 +  Math.abs(centroids[0].getGreen()-temp[0].getGreen() ));
       }while(!centroids[0].equals(temp[0])); /*((double)Math.abs(centroids[0].getRed()-temp[0].getRed()) < eps
    		   && (double) Math.abs(centroids[0].getBlue()-temp[0].getBlue())<eps&&
    		   (double) Math.abs(centroids[0].getGreen()-temp[0].getGreen() )<eps)*/
    	   /*while(equal(centroids,temp )!=false)*/;
         
	//	System.out.print(b);
         
         for(int w=0 ; w<k ; w++) {
		 
			 for(int x=0 ; x<height ; x++) {
    			 for(int y=0 ; y<width ; y++) {
    				 
    				 if(cluster[y][x]==w) {
    					 Color c2=new Color(centroids[w].getRed() , centroids[w].getGreen() , centroids[w].getBlue());
    					 image.setRGB(y, x, c2.getRGB());
    				 }
    			 }
    		 }
			 
		 }
         
         ImageIO.write( image, "jpg", new File (output));
         System.out.println("/*******END*******/");
         
   }
         
    public boolean equal(Color [] a , Color [] b) {
    
    	for(int i=0 ; i<a.length ; i++) {
    		
    		if(a[i].equals(b[i])) {

    			return false ;
    		}
    	}
		return true;
    	
    	
    }
	
	public int minValueIndex(double[] array) {
		
		double temp=Double.MAX_VALUE;
		int index=0;
		
		for(int i=0 ;i<array.length ; i++) {
			
			if(array[i]<temp) {
				temp=array[i];
				index=i;
			}
		}
		return index;
		
	}

}
