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
comment|//  Authors:  Joerg K. Wegner
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

begin_comment
comment|/*==========================================================================*  * IMPORTS  *========================================================================== */
end_comment

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|StringTokenizer
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
comment|/*==========================================================================*  * CLASS DECLARATION  *========================================================================== */
end_comment

begin_comment
comment|/**  * JabRef helper methods.  *  * @author     wegnerj  * @license GPL  * @cvsversion    $Revision$, $Date$  */
end_comment

begin_class
DECL|class|WSITools
specifier|public
class|class
name|WSITools
block|{
comment|//~ Constructors ///////////////////////////////////////////////////////////
DECL|method|WSITools ()
specifier|private
name|WSITools
parameter_list|()
block|{     }
comment|//~ Methods ////////////////////////////////////////////////////////////////
comment|/**      * @param  vcr  {@link java.util.Vector} of<tt>String</tt>      * @param  buf  Description of the Parameter      * @return      Description of the Return Value      */
DECL|method|tokenize (Vector vcr, String buf)
specifier|public
specifier|static
name|boolean
name|tokenize
parameter_list|(
name|Vector
name|vcr
parameter_list|,
name|String
name|buf
parameter_list|)
block|{
return|return
name|tokenize
argument_list|(
name|vcr
argument_list|,
name|buf
argument_list|,
literal|" \t\n"
argument_list|)
return|;
block|}
comment|/**      * @param  vcr       {@link java.util.Vector} of<tt>String</tt>      * @param  buf       Description of the Parameter      * @param  delimstr  Description of the Parameter      * @return           Description of the Return Value      */
DECL|method|tokenize (Vector vcr, String buf, String delimstr)
specifier|public
specifier|static
name|boolean
name|tokenize
parameter_list|(
name|Vector
name|vcr
parameter_list|,
name|String
name|buf
parameter_list|,
name|String
name|delimstr
parameter_list|)
block|{
name|vcr
operator|.
name|clear
argument_list|()
expr_stmt|;
name|buf
operator|=
operator|new
name|String
argument_list|(
name|buf
operator|+
literal|"\n"
argument_list|)
expr_stmt|;
name|StringTokenizer
name|st
init|=
operator|new
name|StringTokenizer
argument_list|(
name|buf
argument_list|,
name|delimstr
argument_list|)
decl_stmt|;
while|while
condition|(
name|st
operator|.
name|hasMoreTokens
argument_list|()
condition|)
block|{
name|vcr
operator|.
name|add
argument_list|(
name|st
operator|.
name|nextToken
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
literal|true
return|;
block|}
comment|/**      * @param  vcr       {@link java.util.Vector} of<tt>String</tt>      * @param  s         Description of the Parameter      * @param  delimstr  Description of the Parameter      * @param  limit     Description of the Parameter      * @return           Description of the Return Value      */
DECL|method|tokenize (Vector vcr, String s, String delimstr, int limit)
specifier|public
specifier|static
name|boolean
name|tokenize
parameter_list|(
name|Vector
name|vcr
parameter_list|,
name|String
name|s
parameter_list|,
name|String
name|delimstr
parameter_list|,
name|int
name|limit
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Warning: tokenize \""
operator|+
name|s
operator|+
literal|"\""
argument_list|)
expr_stmt|;
name|vcr
operator|.
name|clear
argument_list|()
expr_stmt|;
name|s
operator|=
operator|new
name|String
argument_list|(
name|s
operator|+
literal|"\n"
argument_list|)
expr_stmt|;
name|int
name|endpos
init|=
literal|0
decl_stmt|;
name|int
name|matched
init|=
literal|0
decl_stmt|;
name|StringTokenizer
name|st
init|=
operator|new
name|StringTokenizer
argument_list|(
name|s
argument_list|,
name|delimstr
argument_list|)
decl_stmt|;
while|while
condition|(
name|st
operator|.
name|hasMoreTokens
argument_list|()
condition|)
block|{
name|String
name|tmp
init|=
name|st
operator|.
name|nextToken
argument_list|()
decl_stmt|;
name|vcr
operator|.
name|add
argument_list|(
name|tmp
argument_list|)
expr_stmt|;
name|matched
operator|++
expr_stmt|;
if|if
condition|(
name|matched
operator|==
name|limit
condition|)
block|{
name|endpos
operator|=
name|s
operator|.
name|lastIndexOf
argument_list|(
name|tmp
argument_list|)
expr_stmt|;
name|vcr
operator|.
name|add
argument_list|(
name|s
operator|.
name|substring
argument_list|(
name|endpos
operator|+
name|tmp
operator|.
name|length
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
return|return
literal|true
return|;
block|}
block|}
end_class

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

