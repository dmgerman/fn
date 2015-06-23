begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*   * Copyright (C) 2006 Jabref-Team  *               2005 Dept. Computer Architecture, University of Tuebingen, Germany  *               2005 Joerg K. Wegner  *               2003 Morten O. Alver, Nizar N. Batada  *                 * All programs in this directory and subdirectories are published under the GNU  * General Public License as described below.  *  * This program is free software; you can redistribute it and/or modify it under  * the terms of the GNU General Public License as published by the Free Software  * Foundation; either version 2 of the License, or (at your option) any later  * version.  *  * This program is distributed in the hope that it will be useful, but WITHOUT  * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS  * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more  * details.  *  * You should have received a copy of the GNU General Public License along with  * this program; if not, write to the Free Software Foundation, Inc., 59 Temple  * Place, Suite 330, Boston, MA 02111-1307 USA  *  * Further information about the GNU GPL is available at:  * http://www.gnu.org/copyleft/gpl.ja.html  *  */
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
name|Globals
import|;
end_import

begin_comment
comment|/**  * JournalAbbreviator formats the given text in an abbreviated form  * according to the journal abbreviation lists.  *   * The given input text is abbreviated according to the journal abbreviation lists.  * If no abbreviation for input is found (e.g. not in list or already abbreviated), the input will be returned unmodified.  *   * Usage:  *    \format[JournalAbbreviator]{\journal}  * Example result:  *    "Phys. Rev. Lett." instead of "Physical Review Letters"  *   * @author  Meigel  *   */
end_comment

begin_class
DECL|class|JournalAbbreviator
specifier|public
class|class
name|JournalAbbreviator
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
name|String
name|strJournal
init|=
name|Globals
operator|.
name|journalAbbrev
operator|.
name|getAbbreviatedName
argument_list|(
name|fieldText
argument_list|,
literal|true
argument_list|)
decl_stmt|;
if|if
condition|(
literal|null
operator|==
name|strJournal
condition|)
block|{
comment|// no abbreviation found => use unabbreviated journal name
name|strJournal
operator|=
name|fieldText
expr_stmt|;
block|}
return|return
name|strJournal
return|;
block|}
block|}
end_class

end_unit

