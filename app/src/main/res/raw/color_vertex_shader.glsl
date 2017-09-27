uniform mat4 u_MVPMatrix;		// A constant representing the combined model/view/projection matrix.      		             		
		  			
attribute vec4 a_Position;		// Per-vertex position information we will pass in.
		  
// The entry point for our vertex shader.  
void main()                                                 	
{
	// gl_Position is a special variable used to store the final position.
	// Multiply the vertex by the matrix to get the final point in normalized screen coordinates.
	gl_Position = u_MVPMatrix * a_Position;                       		  
}                                                          