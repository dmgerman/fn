begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Copyright (C) 2004-2006 Jabref-Team  *   * All programs in this directory and subdirectories are published under the GNU  * General Public License as described below.  *  * This program is free software; you can redistribute it and/or modify it under  * the terms of the GNU General Public License as published by the Free Software  * Foundation; either version 2 of the License, or (at your option) any later  * version.  *  * This program is distributed in the hope that it will be useful, but WITHOUT  * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS  * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more  * details.  *  * You should have received a copy of the GNU General Public License along with  * this program; if not, write to the Free Software Foundation, Inc., 59 Temple  * Place, Suite 330, Boston, MA 02111-1307 USA  *  * Further information about the GNU GPL is available at:  * http://www.gnu.org/copyleft/gpl.ja.html  *  */
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

begin_comment
comment|/**  * Duplicate of AuthorLastFirstAbbreviator.  *   * @see AuthorLastFirstAbbreviator  *   * @author Carlos Silla  */
end_comment

begin_class
DECL|class|AuthorAbbreviator
specifier|public
class|class
name|AuthorAbbreviator
implements|implements
name|LayoutFormatter
block|{
comment|/* 	 * (non-Javadoc) 	 *  	 * @see net.sf.jabref.export.layout.LayoutFormatter#format(java.lang.String) 	 */
DECL|method|format (String fieldText)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|fieldText
parameter_list|)
block|{
name|AuthorList
name|list
init|=
name|AuthorList
operator|.
name|getAuthorList
argument_list|(
name|fieldText
argument_list|)
decl_stmt|;
return|return
name|list
operator|.
name|getAuthorsLastFirstAnds
argument_list|(
literal|true
argument_list|)
return|;
block|}
block|}
end_class

end_unit

