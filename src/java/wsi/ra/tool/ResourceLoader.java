begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|///////////////////////////////////////////////////////////////////////////////
end_comment

begin_comment
comment|//  Filename: $RCSfile$
end_comment

begin_comment
comment|//  Purpose:  Atom representation.
end_comment

begin_comment
comment|//  Language: Java
end_comment

begin_comment
comment|//  Compiler: JDK 1.4
end_comment

begin_comment
comment|//  Authors:  Joerg K. Wegner, Gerd Mueller
end_comment

begin_comment
comment|//  Version:  $Revision$
end_comment

begin_comment
comment|//            $Date$
end_comment

begin_comment
comment|//            $Author$
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//  Copyright (c) Dept. Computer Architecture, University of Tuebingen, Germany
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//  This program is free software; you can redistribute it and/or modify
end_comment

begin_comment
comment|//  it under the terms of the GNU General Public License as published by
end_comment

begin_comment
comment|//  the Free Software Foundation version 2 of the License.
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//  This program is distributed in the hope that it will be useful,
end_comment

begin_comment
comment|//  but WITHOUT ANY WARRANTY; without even the implied warranty of
end_comment

begin_comment
comment|//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
end_comment

begin_comment
comment|//  GNU General Public License for more details.
end_comment

begin_comment
comment|///////////////////////////////////////////////////////////////////////////////
end_comment

begin_package
DECL|package|wsi.ra.tool
package|package
name|wsi
operator|.
name|ra
operator|.
name|tool
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|*
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Vector
import|;
end_import

begin_comment
comment|/*==========================================================================*  * IMPORTS  *==========================================================================*/
end_comment

begin_comment
comment|//import org.apache.log4j.*;
end_comment

begin_comment
comment|/*==========================================================================*  * CLASS DECLARATION  *==========================================================================*/
end_comment

begin_comment
comment|/**  *  Loads resource file from directory OR jar file. Now it is easier possible to  *  access resource files in a directory structure or a .jar/.zip file.  *  * @author     wegnerj  * @author     Robin Friedman, rfriedman@TriadTherapeutics.com  * @author     Gerd Mueller  * @license GPL  * @cvsversion    $Revision$, $Date$  */
end_comment

begin_class
DECL|class|ResourceLoader
specifier|public
class|class
name|ResourceLoader
block|{
comment|//~ Static fields/initializers /////////////////////////////////////////////
comment|/**      *  Obtain a suitable logger.      */
comment|//    private static Category logger = Category.getInstance(
comment|//            "wsi.ra.tool.ResourceLoader");
DECL|field|resourceLoader
specifier|private
specifier|static
name|ResourceLoader
name|resourceLoader
decl_stmt|;
comment|//~ Constructors ///////////////////////////////////////////////////////////
comment|/*------------------------------------------------------------------------*      * constructor      *------------------------------------------------------------------------  */
comment|/**      *  Constructor for the ResourceLoader object      */
DECL|method|ResourceLoader ()
specifier|private
name|ResourceLoader
parameter_list|()
block|{     }
comment|//~ Methods ////////////////////////////////////////////////////////////////
comment|/*-------------------------------------------------------------------------*      * public methods      *-------------------------------------------------------------------------  */
comment|/**      *  Description of the Method      *      * @return    Description of the Return Value      */
DECL|method|instance ()
specifier|public
specifier|static
specifier|synchronized
name|ResourceLoader
name|instance
parameter_list|()
block|{
if|if
condition|(
name|resourceLoader
operator|==
literal|null
condition|)
block|{
name|resourceLoader
operator|=
operator|new
name|ResourceLoader
argument_list|()
expr_stmt|;
block|}
return|return
name|resourceLoader
return|;
block|}
comment|/**      *  Gets the byte data from a file at the given resource location.      *      * @param  resourceLocation  Description of the Parameter      * @return                   the byte array of file.      */
DECL|method|getBytesFromResourceLocation (String resourceLocation)
specifier|public
name|byte
index|[]
name|getBytesFromResourceLocation
parameter_list|(
name|String
name|resourceLocation
parameter_list|)
block|{
if|if
condition|(
name|resourceLocation
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
comment|// to avoid hours of debugging non-found-files under linux with
comment|// some f... special characters at the end which will not be shown
comment|// at the console output !!!
name|resourceLocation
operator|=
name|resourceLocation
operator|.
name|trim
argument_list|()
expr_stmt|;
comment|// is a relative path defined ?
comment|// this can only be possible, if this is a file resource loacation
if|if
condition|(
name|resourceLocation
operator|.
name|startsWith
argument_list|(
literal|".."
argument_list|)
operator|||
name|resourceLocation
operator|.
name|startsWith
argument_list|(
literal|"/"
argument_list|)
operator|||
name|resourceLocation
operator|.
name|startsWith
argument_list|(
literal|"\\"
argument_list|)
operator|||
operator|(
operator|(
name|resourceLocation
operator|.
name|length
argument_list|()
operator|>
literal|1
operator|)
operator|&&
operator|(
name|resourceLocation
operator|.
name|charAt
argument_list|(
literal|1
argument_list|)
operator|==
literal|':'
operator|)
operator|)
condition|)
block|{
return|return
name|getBytesFromFile
argument_list|(
name|resourceLocation
argument_list|)
return|;
block|}
name|InputStream
name|in
init|=
name|ClassLoader
operator|.
name|getSystemResourceAsStream
argument_list|(
name|resourceLocation
argument_list|)
decl_stmt|;
if|if
condition|(
name|in
operator|==
literal|null
condition|)
block|{
comment|// try again for web start applications
name|in
operator|=
name|this
operator|.
name|getClass
argument_list|()
operator|.
name|getClassLoader
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
name|resourceLocation
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|in
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
name|byte
name|bytes
index|[]
init|=
name|getBytesFromStream
argument_list|(
name|in
argument_list|)
decl_stmt|;
comment|//		if(bytes==null)
comment|//		{
comment|//			URL location = this.getClass().getClassLoader().getSystemResource(resourceLocation);
comment|//			String fileLocation = location.getFile();
comment|//			bytes=getBytesFromFile(fileLocation);
comment|//		}
return|return
name|bytes
return|;
comment|//        //System.out.println(this.getClass().getClassLoader().getSystemResource(resourceLocation));
comment|//        URL location = this.getClass().getClassLoader().getSystemResource(resourceLocation);
comment|//
comment|//        if (location == null)
comment|//        {
comment|//            // try again for web start applications
comment|//            location = this.getClass().getClassLoader().getResource(resourceLocation);
comment|//        }
comment|//
comment|//        if (logger.isDebugEnabled())
comment|//        {
comment|//            logger.debug("Trying to get " + resourceLocation + " from URL: " +
comment|//                location);
comment|//        }
comment|//
comment|//        if (location == null)
comment|//        {
comment|//            return null;
comment|//        }
comment|//
comment|//        String locationString = URLDecoder.decode( location.toString() );
comment|//
comment|//        int posJAR = locationString.indexOf(".jar!");
comment|//        int posZIP = locationString.indexOf(".zip!");
comment|//        int pos = -1;
comment|//
comment|//        if ((posJAR> -1)&& (posZIP> -1))
comment|//        {
comment|//            pos = Math.min(posJAR, posZIP);
comment|//        }
comment|//        else if (posJAR> -1)
comment|//        {
comment|//            pos = posJAR;
comment|//        }
comment|//        else if (posZIP> -1)
comment|//        {
comment|//            pos = posZIP;
comment|//        }
comment|//
comment|//        // is the resource file in a zip or a jar file
comment|//        if (pos> -1)
comment|//        {
comment|//            // load it from zip or jar file
comment|//            String urlToZip = locationString.substring(4, pos + 4);
comment|//            String internalArchivePath = locationString.substring(pos + 6);
comment|//
comment|//            if (logger.isDebugEnabled())
comment|//            {
comment|//                logger.debug("Loading " + internalArchivePath +
comment|//                    " from archive " + urlToZip + ".");
comment|//            }
comment|//
comment|//            return getBytesFromArchive(urlToZip, internalArchivePath);
comment|//        }
comment|//        else
comment|//        {
comment|//            String fileLocation = location.getFile();
comment|//
comment|//            // load it from an unpacked file
comment|//            if (logger.isDebugEnabled())
comment|//            {
comment|//                logger.debug("Loading from file " + fileLocation + ".");
comment|//            }
comment|//
comment|//            return getBytesFromFile(fileLocation);
comment|//        }
block|}
comment|/**      *  Description of the Method      *      * @param  resourceFile  Description of the Parameter      * @return               Description of the Return Value      */
DECL|method|readLines (String resourceFile)
specifier|public
specifier|static
name|Vector
argument_list|<
name|String
argument_list|>
name|readLines
parameter_list|(
name|String
name|resourceFile
parameter_list|)
block|{
return|return
name|readLines
argument_list|(
name|resourceFile
argument_list|,
literal|false
argument_list|)
return|;
block|}
comment|/**      *  Description of the Method      *      * @param  resourceFile    Description of the Parameter      * @param  ignoreComments  Description of the Parameter      * @return                 Description of the Return Value      */
DECL|method|readLines (String resourceFile, boolean ignoreCommentedLines)
specifier|public
specifier|static
name|Vector
argument_list|<
name|String
argument_list|>
name|readLines
parameter_list|(
name|String
name|resourceFile
parameter_list|,
name|boolean
name|ignoreCommentedLines
parameter_list|)
block|{
if|if
condition|(
name|resourceFile
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
name|byte
index|[]
name|bytes
init|=
name|ResourceLoader
operator|.
name|instance
argument_list|()
operator|.
name|getBytesFromResourceLocation
argument_list|(
name|resourceFile
argument_list|)
decl_stmt|;
if|if
condition|(
name|bytes
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
name|ByteArrayInputStream
name|sReader
init|=
operator|new
name|ByteArrayInputStream
argument_list|(
name|bytes
argument_list|)
decl_stmt|;
name|LineNumberReader
name|lnr
init|=
operator|new
name|LineNumberReader
argument_list|(
operator|new
name|InputStreamReader
argument_list|(
name|sReader
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|line
decl_stmt|;
name|Vector
argument_list|<
name|String
argument_list|>
name|vector
init|=
operator|new
name|Vector
argument_list|<
name|String
argument_list|>
argument_list|(
literal|100
argument_list|)
decl_stmt|;
try|try
block|{
while|while
condition|(
operator|(
name|line
operator|=
name|lnr
operator|.
name|readLine
argument_list|()
operator|)
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
operator|!
name|ignoreCommentedLines
condition|)
block|{
if|if
condition|(
operator|!
operator|(
name|line
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|==
literal|'#'
operator|)
condition|)
block|{
name|vector
operator|.
name|add
argument_list|(
name|line
argument_list|)
expr_stmt|;
comment|//		  System.out.println("ADD:"+line);
block|}
block|}
else|else
block|{
name|vector
operator|.
name|add
argument_list|(
name|line
argument_list|)
expr_stmt|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
name|ex
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
return|return
name|vector
return|;
block|}
comment|/*-------------------------------------------------------------------------*      * private methods      *-------------------------------------------------------------------------  */
comment|/**      *  Gets the byte data from a file.      *      * @param  fileName  Description of the Parameter      * @return           the byte array of the file.      */
DECL|method|getBytesFromFile (String fileName)
specifier|private
name|byte
index|[]
name|getBytesFromFile
parameter_list|(
name|String
name|fileName
parameter_list|)
block|{
if|if
condition|(
name|fileName
operator|.
name|startsWith
argument_list|(
literal|"/cygdrive/"
argument_list|)
condition|)
block|{
name|int
name|length
init|=
literal|"/cygdrive/"
operator|.
name|length
argument_list|()
decl_stmt|;
name|fileName
operator|=
name|fileName
operator|.
name|substring
argument_list|(
name|length
argument_list|,
name|length
operator|+
literal|1
argument_list|)
operator|+
literal|":"
operator|+
name|fileName
operator|.
name|substring
argument_list|(
name|length
operator|+
literal|1
argument_list|)
expr_stmt|;
block|}
comment|//if (logger.isDebugEnabled())
comment|//{
comment|//    logger.debug("Trying to get file from " + fileName);
comment|//}
name|File
name|file
init|=
operator|new
name|File
argument_list|(
name|fileName
argument_list|)
decl_stmt|;
name|FileInputStream
name|fis
init|=
literal|null
decl_stmt|;
try|try
block|{
name|fis
operator|=
operator|new
name|FileInputStream
argument_list|(
name|file
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
return|return
literal|null
return|;
block|}
name|BufferedInputStream
name|bis
init|=
operator|new
name|BufferedInputStream
argument_list|(
name|fis
argument_list|)
decl_stmt|;
comment|// only files with<65536 bytes are allowed
comment|//if( file.length()> 65536 ) System.out.println("Resource files should be smaller than 65536 bytes...");
name|int
name|size
init|=
operator|(
name|int
operator|)
name|file
operator|.
name|length
argument_list|()
decl_stmt|;
name|byte
index|[]
name|b
init|=
operator|new
name|byte
index|[
name|size
index|]
decl_stmt|;
name|int
name|rb
init|=
literal|0
decl_stmt|;
name|int
name|chunk
init|=
literal|0
decl_stmt|;
try|try
block|{
while|while
condition|(
operator|(
name|size
operator|-
name|rb
operator|)
operator|>
literal|0
condition|)
block|{
name|chunk
operator|=
name|bis
operator|.
name|read
argument_list|(
name|b
argument_list|,
name|rb
argument_list|,
name|size
operator|-
name|rb
argument_list|)
expr_stmt|;
if|if
condition|(
name|chunk
operator|==
operator|-
literal|1
condition|)
block|{
break|break;
block|}
name|rb
operator|+=
name|chunk
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
return|return
literal|null
return|;
block|}
return|return
name|b
return|;
block|}
comment|/**      *  Gets the byte data from a file.      *      * @param  fileName  Description of the Parameter      * @return           the byte array of the file.      */
DECL|method|getBytesFromStream (InputStream stream)
specifier|private
name|byte
index|[]
name|getBytesFromStream
parameter_list|(
name|InputStream
name|stream
parameter_list|)
block|{
comment|//if (logger.isDebugEnabled())
comment|//{
comment|//    logger.debug("Trying to get file from stream");
comment|//}
name|BufferedInputStream
name|bis
init|=
operator|new
name|BufferedInputStream
argument_list|(
name|stream
argument_list|)
decl_stmt|;
try|try
block|{
name|int
name|size
init|=
name|bis
operator|.
name|available
argument_list|()
decl_stmt|;
name|byte
index|[]
name|b
init|=
operator|new
name|byte
index|[
name|size
index|]
decl_stmt|;
name|int
name|rb
init|=
literal|0
decl_stmt|;
name|int
name|chunk
init|=
literal|0
decl_stmt|;
while|while
condition|(
operator|(
name|size
operator|-
name|rb
operator|)
operator|>
literal|0
condition|)
block|{
name|chunk
operator|=
name|bis
operator|.
name|read
argument_list|(
name|b
argument_list|,
name|rb
argument_list|,
name|size
operator|-
name|rb
argument_list|)
expr_stmt|;
if|if
condition|(
name|chunk
operator|==
operator|-
literal|1
condition|)
block|{
break|break;
block|}
name|rb
operator|+=
name|chunk
expr_stmt|;
block|}
return|return
name|b
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
return|return
literal|null
return|;
block|}
block|}
block|}
end_class

begin_comment
comment|/*-------------------------------------------------------------------------*  * END  *-------------------------------------------------------------------------*/
end_comment

begin_comment
comment|///////////////////////////////////////////////////////////////////////////////
end_comment

begin_comment
comment|//  END OF FILE.
end_comment

begin_comment
comment|///////////////////////////////////////////////////////////////////////////////
end_comment

end_unit

