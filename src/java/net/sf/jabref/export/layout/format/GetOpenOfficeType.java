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
DECL|package|net.sf.jabref.export.layout.format
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|export
operator|.
name|layout
operator|.
name|format
package|;
end_package

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|export
operator|.
name|layout
operator|.
name|LayoutFormatter
import|;
end_import

begin_comment
comment|/**  * Change type of record to match the one used by OpenOffice formatter.  *   * Based on the RemoveBrackets.java class (Revision 1.2) by mortenalver  * @author $author$  * @version $Revision$  */
end_comment

begin_class
DECL|class|GetOpenOfficeType
specifier|public
class|class
name|GetOpenOfficeType
implements|implements
name|LayoutFormatter
block|{
comment|//~ Methods ////////////////////////////////////////////////////////////////
DECL|method|format (String fieldText)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|fieldText
parameter_list|)
block|{
name|String
name|fieldEntry
init|=
name|fieldText
decl_stmt|;
if|if
condition|(
name|fieldEntry
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"Article"
argument_list|)
condition|)
return|return
literal|"7"
return|;
if|if
condition|(
name|fieldEntry
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"Book"
argument_list|)
condition|)
return|return
literal|"1"
return|;
if|if
condition|(
name|fieldEntry
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"Booklet"
argument_list|)
condition|)
return|return
literal|"2"
return|;
if|if
condition|(
name|fieldEntry
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"Inbook"
argument_list|)
condition|)
return|return
literal|"5"
return|;
if|if
condition|(
name|fieldEntry
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"Incollection"
argument_list|)
condition|)
return|return
literal|"5"
return|;
if|if
condition|(
name|fieldEntry
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"Inproceedings"
argument_list|)
condition|)
return|return
literal|"6"
return|;
if|if
condition|(
name|fieldEntry
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"Manual"
argument_list|)
condition|)
return|return
literal|"8"
return|;
if|if
condition|(
name|fieldEntry
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"Mastersthesis"
argument_list|)
condition|)
return|return
literal|"9"
return|;
if|if
condition|(
name|fieldEntry
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"Misc"
argument_list|)
condition|)
return|return
literal|"10"
return|;
if|if
condition|(
name|fieldEntry
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"Other"
argument_list|)
condition|)
return|return
literal|"10"
return|;
if|if
condition|(
name|fieldEntry
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"Phdthesis"
argument_list|)
condition|)
return|return
literal|"9"
return|;
if|if
condition|(
name|fieldEntry
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"Proceedings"
argument_list|)
condition|)
return|return
literal|"3"
return|;
if|if
condition|(
name|fieldEntry
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"Techreport"
argument_list|)
condition|)
return|return
literal|"13"
return|;
if|if
condition|(
name|fieldEntry
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"Unpublished"
argument_list|)
condition|)
return|return
literal|"14"
return|;
comment|// Default, Miscelaneous
return|return
literal|"10"
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

