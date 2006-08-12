begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Copyright (C) 2005-2006 Jabref-Team  *   * All programs in this directory and subdirectories are published under the GNU  * General Public License as described below.  *  * This program is free software; you can redistribute it and/or modify it under  * the terms of the GNU General Public License as published by the Free Software  * Foundation; either version 2 of the License, or (at your option) any later  * version.  *  * This program is distributed in the hope that it will be useful, but WITHOUT  * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS  * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more  * details.  *  * You should have received a copy of the GNU General Public License along with  * this program; if not, write to the Free Software Foundation, Inc., 59 Temple  * Place, Suite 330, Boston, MA 02111-1307 USA  *  * Further information about the GNU GPL is available at:  * http://www.gnu.org/copyleft/gpl.ja.html  *  */
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
comment|/**  *<ul>  *<li>Names are given in order: von last, jr, first.</li>  *<li>First names will be abbreviated.</li>  *<li>Individual authors are separated by commas.</li>  *<li>The 'and' of a list of three or more authors is preceeded by a comma  * (Oxford comma)</li>  *</ul>  *   * @author mkovtun  * @author Christopher Oezbek<oezi@oezi.de>  *   */
end_comment

begin_class
DECL|class|AuthorLastFirstAbbrOxfordCommas
specifier|public
class|class
name|AuthorLastFirstAbbrOxfordCommas
implements|implements
name|LayoutFormatter
block|{
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
name|AuthorList
operator|.
name|fixAuthor_lastNameFirstCommas
argument_list|(
name|fieldText
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
return|;
block|}
block|}
end_class

end_unit

