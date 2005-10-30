begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Version:  $Revision$               $Date$               $Author$      Copyright (c) Dept. Computer Architecture, University of Tuebingen, Germany      This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation version 2 of the License.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.   ///////////////////////////////////////////////////////////////////////////// */
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

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|AuthorList
import|;
end_import

begin_class
DECL|class|AuthorLastFirst
specifier|public
class|class
name|AuthorLastFirst
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
comment|//ConvertSpecialCharactersForHTML conv = new ConvertSpecialCharactersForHTML();
comment|//return conv.format(ImportFormatReader.fixAuthor_lastNameFirst(fieldText));
return|return
name|AuthorList
operator|.
name|fixAuthor_lastNameFirst
argument_list|(
name|fieldText
argument_list|)
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

