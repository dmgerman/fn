begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
comment|/**  * Uses as input the fields (author or editor) in the LastFirst format.  *   * This formater enables to abbreviate the authors name in the following way:  *   * Ex: Someone, Van Something will be abbreviated as Someone, V.S.  *   * @author Carlos Silla  * @author Christopher Oezbek<oezi@oezi.de>  *   * @version 1.0 Created on 12/10/2004  * @version 1.1 Fixed bug  *          http://sourceforge.net/tracker/index.php?func=detail&aid=1466924&group_id=92314&atid=600306  */
end_comment

begin_class
DECL|class|AuthorLastFirstAbbreviator
specifier|public
class|class
name|AuthorLastFirstAbbreviator
implements|implements
name|LayoutFormatter
block|{
comment|/**      * @see net.sf.jabref.export.layout.LayoutFormatter#format(java.lang.String)      */
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
comment|/**          * This formatter is a duplicate of AuthorAbbreviator, so we simply          * call that one.          */
return|return
operator|new
name|AuthorAbbreviator
argument_list|()
operator|.
name|format
argument_list|(
name|fieldText
argument_list|)
return|;
block|}
block|}
end_class

end_unit

