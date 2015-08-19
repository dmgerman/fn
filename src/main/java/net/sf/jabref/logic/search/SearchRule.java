begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2003  Nathan Dunn  All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  */
end_comment

begin_package
DECL|package|net.sf.jabref.logic.search
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|search
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
name|model
operator|.
name|entry
operator|.
name|BibtexEntry
import|;
end_import

begin_interface
DECL|interface|SearchRule
specifier|public
interface|interface
name|SearchRule
block|{
comment|/*      * Because some rules require the query in the constructor,      * the parameter query is not always used as expected.      * The two constants provide means to mark this as dummy.      * As I am not sure whether null could be substituted by "dummy" I leave everything as is.      */
DECL|field|DUMMY_QUERY
name|String
name|DUMMY_QUERY
init|=
literal|"dummy"
decl_stmt|;
DECL|field|NULL_QUERY
name|String
name|NULL_QUERY
init|=
literal|null
decl_stmt|;
DECL|method|applyRule (String query, BibtexEntry bibtexEntry)
name|boolean
name|applyRule
parameter_list|(
name|String
name|query
parameter_list|,
name|BibtexEntry
name|bibtexEntry
parameter_list|)
function_decl|;
DECL|method|validateSearchStrings (String query)
name|boolean
name|validateSearchStrings
parameter_list|(
name|String
name|query
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

