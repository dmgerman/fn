begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2003 Morten O. Alver, Nizar N. Batada  All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  */
end_comment

begin_package
DECL|package|net.sf.jabref
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
import|;
end_import

begin_class
DECL|class|ParserResult
specifier|public
class|class
name|ParserResult
block|{
DECL|field|base
specifier|private
name|BibtexDatabase
name|base
decl_stmt|;
DECL|field|metaData
specifier|private
name|HashMap
name|metaData
decl_stmt|;
DECL|method|ParserResult (BibtexDatabase base, HashMap metaData)
specifier|public
name|ParserResult
parameter_list|(
name|BibtexDatabase
name|base
parameter_list|,
name|HashMap
name|metaData
parameter_list|)
block|{
name|this
operator|.
name|base
operator|=
name|base
expr_stmt|;
name|this
operator|.
name|metaData
operator|=
name|metaData
expr_stmt|;
block|}
DECL|method|getDatabase ()
specifier|public
name|BibtexDatabase
name|getDatabase
parameter_list|()
block|{
return|return
name|base
return|;
block|}
DECL|method|getMetaData ()
specifier|public
name|HashMap
name|getMetaData
parameter_list|()
block|{
return|return
name|metaData
return|;
block|}
block|}
end_class

end_unit

