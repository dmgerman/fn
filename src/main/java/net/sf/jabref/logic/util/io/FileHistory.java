begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.logic.util.io
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
operator|.
name|io
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|LinkedList
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
name|JabRefPreferences
import|;
end_import

begin_class
DECL|class|FileHistory
specifier|public
class|class
name|FileHistory
block|{
DECL|field|prefs
specifier|private
specifier|final
name|JabRefPreferences
name|prefs
decl_stmt|;
DECL|field|history
specifier|private
specifier|final
name|LinkedList
argument_list|<
name|String
argument_list|>
name|history
init|=
operator|new
name|LinkedList
argument_list|<>
argument_list|()
decl_stmt|;
DECL|method|FileHistory (JabRefPreferences prefs)
specifier|public
name|FileHistory
parameter_list|(
name|JabRefPreferences
name|prefs
parameter_list|)
block|{
name|this
operator|.
name|prefs
operator|=
name|prefs
expr_stmt|;
name|String
index|[]
name|old
init|=
name|prefs
operator|.
name|getStringArray
argument_list|(
name|JabRefPreferences
operator|.
name|RECENT_FILES
argument_list|)
decl_stmt|;
if|if
condition|(
name|old
operator|!=
literal|null
condition|)
block|{
name|history
operator|.
name|addAll
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|old
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|size ()
specifier|public
name|int
name|size
parameter_list|()
block|{
return|return
name|history
operator|.
name|size
argument_list|()
return|;
block|}
comment|/**      * Adds the filename to the top of the list. If it already is in the list, it is merely moved to the top.      *      * @param filename a<code>String</code> value      */
DECL|method|newFile (String filename)
specifier|public
name|void
name|newFile
parameter_list|(
name|String
name|filename
parameter_list|)
block|{
name|history
operator|.
name|remove
argument_list|(
name|filename
argument_list|)
expr_stmt|;
name|history
operator|.
name|addFirst
argument_list|(
name|filename
argument_list|)
expr_stmt|;
while|while
condition|(
name|history
operator|.
name|size
argument_list|()
operator|>
name|prefs
operator|.
name|getInt
argument_list|(
name|JabRefPreferences
operator|.
name|HISTORY_SIZE
argument_list|)
condition|)
block|{
name|history
operator|.
name|removeLast
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|getFileName (int i)
specifier|public
name|String
name|getFileName
parameter_list|(
name|int
name|i
parameter_list|)
block|{
return|return
name|history
operator|.
name|get
argument_list|(
name|i
argument_list|)
return|;
block|}
DECL|method|removeItem (String filename)
specifier|public
name|void
name|removeItem
parameter_list|(
name|String
name|filename
parameter_list|)
block|{
name|history
operator|.
name|remove
argument_list|(
name|filename
argument_list|)
expr_stmt|;
block|}
DECL|method|storeHistory ()
specifier|public
name|void
name|storeHistory
parameter_list|()
block|{
if|if
condition|(
operator|!
name|history
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|String
index|[]
name|names
init|=
name|history
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|history
operator|.
name|size
argument_list|()
index|]
argument_list|)
decl_stmt|;
name|prefs
operator|.
name|putStringArray
argument_list|(
name|JabRefPreferences
operator|.
name|RECENT_FILES
argument_list|,
name|names
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

