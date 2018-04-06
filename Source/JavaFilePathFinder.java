import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Class that gets a list of paths to .java files
 * 
 */
public class JavaFilePathFinder {

	
	// Have the list as a class field that all methods can access
	LinkedList<String> list = new LinkedList<>();
	
	/**
	 * METHOD: getJavaFiles()
	 * <p>
	 * Use this to get all the .java files from a directory
	 * 
	 * @param directoryPath
	 * 			A String that is assigned to the path of the target directory
	 * 
	 * @return
	 * 			A String[] of the source code of all the .java files in the target directory
	 * @throws IllegalArgumentException 
	 *          If the path of the target directory doesn't exist
	 * @throws IOException 
	 */

	public String[] getJavaFiles(String directoryPath) throws IllegalArgumentException, IOException{ 
		File directory = new File(directoryPath);
		File[] javaFiles = directory.listFiles();

		if (directoryPath == null) {
			throw new IllegalArgumentException();
		}
		
		if (directory.isDirectory() == false) {
			throw new IllegalArgumentException();
		}
		
		for (int i = 0; i < javaFiles.length ;  i++) {
			String filePath = javaFiles[i].getAbsolutePath();	
			//String fileExtension = filePath.substring(filePath.lastIndexOf("."));			//Gave bugs, used filePath.endsWith() instead
			
			// read the  '.java' file into a string add the string to the list.
	        if (filePath.endsWith(".java")) {
	        	//System.out.println("here");
	        	StringBuilder inputSource = new StringBuilder();
	    		BufferedReader inputFile = Files.newBufferedReader(Paths.get(filePath), Charset.defaultCharset());
	    		//System.out.println(inputSource);
	    		String result = "";
				// Read .java file into a string for the ASTParser
				do {
					result = inputFile.readLine();
					if (result != null) {
						inputSource.append(result);
						inputSource.append("\n");
					}
				} while (result != null);
				list.add(inputSource.toString());
				
	           
			// call the jar file method	
	        } else if (filePath.endsWith(".jar")){
	            readFromJar(filePath);
	        	
	    	} else if (javaFiles[i].isDirectory() == true) {  // Recursive call for directory parsing
	           getJavaFiles(filePath);
	    	}	
	        
		}
		
		
		String[] sourceArray = list.toArray(new String[list.size()]);
	    return sourceArray;  
	
	}
	
	/**
     * Reads all files with the .java file extension inside the current directory into strings.
     * Throws an IOException if the JarFile constructor or the input stream does.
     * If the given path is null or does not have a .jar file extension the method returns without doing anything.
     *
     * JarFile constructor documented to throw IOException "if an I/O error has occured"
     * the exact terms of that are unknown.
     * Known causes: the given name does not correspond to a file, the given file is not a .jar file
     */
	private void readFromJar (String path) throws IOException {
		
	        if (path == null)
	            return;
	        if (!path.endsWith(".jar"))
	            return;
	        
	        JarFile jar = new JarFile(path);
	        Enumeration entries = jar.entries();    // Get the files inside the .jar file
	        while (entries.hasMoreElements()) {
	            JarEntry ent = (JarEntry) entries.nextElement();
	            
	            // If the file has a .java extension, get an InputStream for that file and
	            // read the file into a string.
	            if(ent.getName().endsWith(".java")) {
	                InputStream in = jar.getInputStream(ent);
	                StringBuilder sb = new StringBuilder();
	                byte[] buff = new byte[4096];
	                int i = 0;
	                while ((i = in.read(buff)) > 0)         // read from file into the buffer
	                    sb.append(new String(buff, 0, i));  // convert the bytes into a string and add to StringBuilder ** Uses system default charset
	                list.add(sb.toString());
	            }
	        }
	        return;
	    }
	    
	
	
}
