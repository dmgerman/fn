begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.external
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|external
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

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Optional
import|;
end_import

begin_comment
comment|/**  * This interface is used for classes that try to resolve a full-text PDF url for a BibTex entry.  * Implementing classes should specialize on specific article sites.  * See e.g. @link{http://libguides.mit.edu/apis}.  */
end_comment

begin_interface
DECL|interface|FullTextFinder
specifier|public
interface|interface
name|FullTextFinder
block|{
comment|/**      * Tries to find a fulltext URL for a given BibTex entry.      *      * @param entry The Bibtex entry      * @return The fulltext PDF URL Optional, if found, or an empty Optional if not found.      * @throws NullPointerException if no BibTex entry is given      * @throws java.io.IOException      */
DECL|method|findFullText (BibtexEntry entry)
name|Optional
argument_list|<
name|URL
argument_list|>
name|findFullText
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
throws|throws
name|IOException
function_decl|;
block|}
end_interface

end_unit

