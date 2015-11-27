begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

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
DECL|package|net.sf.jabref.exporter.layout.format
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|exporter
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
name|exporter
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
annotation|@
name|Override
DECL|method|format (String fieldText)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|fieldText
parameter_list|)
block|{
if|if
condition|(
literal|"Article"
operator|.
name|equalsIgnoreCase
argument_list|(
name|fieldText
argument_list|)
condition|)
block|{
return|return
literal|"7"
return|;
block|}
if|if
condition|(
literal|"Book"
operator|.
name|equalsIgnoreCase
argument_list|(
name|fieldText
argument_list|)
condition|)
block|{
return|return
literal|"1"
return|;
block|}
if|if
condition|(
literal|"Booklet"
operator|.
name|equalsIgnoreCase
argument_list|(
name|fieldText
argument_list|)
condition|)
block|{
return|return
literal|"2"
return|;
block|}
if|if
condition|(
literal|"Inbook"
operator|.
name|equalsIgnoreCase
argument_list|(
name|fieldText
argument_list|)
condition|)
block|{
return|return
literal|"5"
return|;
block|}
if|if
condition|(
literal|"Incollection"
operator|.
name|equalsIgnoreCase
argument_list|(
name|fieldText
argument_list|)
condition|)
block|{
return|return
literal|"5"
return|;
block|}
if|if
condition|(
literal|"Inproceedings"
operator|.
name|equalsIgnoreCase
argument_list|(
name|fieldText
argument_list|)
condition|)
block|{
return|return
literal|"6"
return|;
block|}
if|if
condition|(
literal|"Manual"
operator|.
name|equalsIgnoreCase
argument_list|(
name|fieldText
argument_list|)
condition|)
block|{
return|return
literal|"8"
return|;
block|}
if|if
condition|(
literal|"Mastersthesis"
operator|.
name|equalsIgnoreCase
argument_list|(
name|fieldText
argument_list|)
condition|)
block|{
return|return
literal|"9"
return|;
block|}
if|if
condition|(
literal|"Misc"
operator|.
name|equalsIgnoreCase
argument_list|(
name|fieldText
argument_list|)
condition|)
block|{
return|return
literal|"10"
return|;
block|}
if|if
condition|(
literal|"Other"
operator|.
name|equalsIgnoreCase
argument_list|(
name|fieldText
argument_list|)
condition|)
block|{
return|return
literal|"10"
return|;
block|}
if|if
condition|(
literal|"Phdthesis"
operator|.
name|equalsIgnoreCase
argument_list|(
name|fieldText
argument_list|)
condition|)
block|{
return|return
literal|"9"
return|;
block|}
if|if
condition|(
literal|"Proceedings"
operator|.
name|equalsIgnoreCase
argument_list|(
name|fieldText
argument_list|)
condition|)
block|{
return|return
literal|"3"
return|;
block|}
if|if
condition|(
literal|"Techreport"
operator|.
name|equalsIgnoreCase
argument_list|(
name|fieldText
argument_list|)
condition|)
block|{
return|return
literal|"13"
return|;
block|}
if|if
condition|(
literal|"Unpublished"
operator|.
name|equalsIgnoreCase
argument_list|(
name|fieldText
argument_list|)
condition|)
block|{
return|return
literal|"14"
return|;
block|}
comment|// Default, Miscelaneous
return|return
literal|"10"
return|;
block|}
block|}
end_class

end_unit

