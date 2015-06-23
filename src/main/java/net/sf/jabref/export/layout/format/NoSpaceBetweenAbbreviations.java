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
comment|/**  *<p>  * LayoutFormatter that removes the space between abbreviated First names  *</p>  *<p>  * What out that this regular expression might also remove other spaces that fit  * the pattern.  *</p>  *<p>  * Example: J. R. R. Tolkien becomes J.R.R. Tolkien.  *</p>  *<p>  * See Testcase for more examples.  *<p>  *   * @author $Author$  * @version $Revision$ ($Date$)  *   */
end_comment

begin_class
DECL|class|NoSpaceBetweenAbbreviations
specifier|public
class|class
name|NoSpaceBetweenAbbreviations
implements|implements
name|LayoutFormatter
block|{
comment|/*      * Match '.' followed by spaces followed by uppercase char followed by '.'      * but don't include the last dot into the capturing group.      *       * Replace the match by removing the spaces.      *       * @see net.sf.jabref.export.layout.LayoutFormatter#format(java.lang.String)      */
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
return|return
name|fieldText
operator|.
name|replaceAll
argument_list|(
literal|"\\.\\s+(\\p{Lu})(?=\\.)"
argument_list|,
literal|"\\.$1"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

