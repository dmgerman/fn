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

begin_comment
comment|/**  * Some helper methods for calling external programs.  *  * @author     wegnerj  * @license GPL  * @cvsversion    $Revision$, $Date$  */
end_comment

begin_class
DECL|class|ExternalHelper
specifier|public
class|class
name|ExternalHelper
block|{
comment|//~ Static fields/initializers /////////////////////////////////////////////
DECL|field|OS_WINDOWS
specifier|public
specifier|static
specifier|final
name|String
name|OS_WINDOWS
init|=
literal|"windows"
decl_stmt|;
DECL|field|OS_LINUX
specifier|public
specifier|static
specifier|final
name|String
name|OS_LINUX
init|=
literal|"linux"
decl_stmt|;
DECL|field|OS_SOLARIS
specifier|public
specifier|static
specifier|final
name|String
name|OS_SOLARIS
init|=
literal|"solaris"
decl_stmt|;
comment|//~ Constructors ///////////////////////////////////////////////////////////
comment|/*-------------------------------------------------------------------------*      * constructor      *-------------------------------------------------------------------------*/
comment|/** Don't let anyone instantiate this class */
DECL|method|ExternalHelper ()
specifier|private
name|ExternalHelper
parameter_list|()
block|{     }
comment|//~ Methods ////////////////////////////////////////////////////////////////
comment|/*-------------------------------------------------------------------------*      * private static methods      *-------------------------------------------------------------------------*/
comment|/**      * Returns the name of the operation system.      *      *   @todo maybe move this method to a more common class */
DECL|method|getOperationSystemName ()
specifier|public
specifier|static
name|String
name|getOperationSystemName
parameter_list|()
block|{
name|String
name|osName
init|=
name|System
operator|.
name|getProperty
argument_list|(
literal|"os.name"
argument_list|)
decl_stmt|;
comment|// determine name of operation system and convert it into lower caps without blanks
if|if
condition|(
name|osName
operator|.
name|indexOf
argument_list|(
literal|"Windows"
argument_list|)
operator|!=
operator|-
literal|1
condition|)
block|{
name|osName
operator|=
name|OS_WINDOWS
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|osName
operator|.
name|indexOf
argument_list|(
literal|"Linux"
argument_list|)
operator|!=
operator|-
literal|1
condition|)
block|{
name|osName
operator|=
name|OS_LINUX
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|osName
operator|.
name|indexOf
argument_list|(
literal|"Solaris"
argument_list|)
operator|!=
operator|-
literal|1
condition|)
block|{
name|osName
operator|=
name|OS_SOLARIS
expr_stmt|;
block|}
return|return
name|osName
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

