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
name|io
operator|.
name|File
import|;
end_import

begin_class
DECL|class|JabRef
specifier|public
class|class
name|JabRef
block|{
DECL|method|main (String[] args)
specifier|public
specifier|static
name|void
name|main
parameter_list|(
name|String
index|[]
name|args
parameter_list|)
block|{
comment|//Globals.turnOffLogging();
name|JabRefPreferences
name|prefs
init|=
operator|new
name|JabRefPreferences
argument_list|()
decl_stmt|;
name|BibtexEntryType
operator|.
name|loadCustomEntryTypes
argument_list|(
name|prefs
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|setLanguage
argument_list|(
name|prefs
operator|.
name|get
argument_list|(
literal|"language"
argument_list|)
argument_list|,
literal|""
argument_list|)
expr_stmt|;
name|JabRefFrame
name|jrf
init|=
operator|new
name|JabRefFrame
argument_list|()
decl_stmt|;
if|if
condition|(
name|args
operator|.
name|length
operator|>
literal|0
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Opening: "
operator|+
name|args
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
name|jrf
operator|.
name|output
argument_list|(
literal|"Opening: "
operator|+
name|args
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
comment|//verify the file
name|File
name|f
init|=
operator|new
name|File
argument_list|(
name|args
index|[
literal|0
index|]
argument_list|)
decl_stmt|;
if|if
condition|(
name|f
operator|.
name|exists
argument_list|()
operator|&&
name|f
operator|.
name|canRead
argument_list|()
operator|&&
name|f
operator|.
name|isFile
argument_list|()
condition|)
block|{
name|jrf
operator|.
name|fileToOpen
operator|=
name|f
expr_stmt|;
name|jrf
operator|.
name|openDatabaseAction
operator|.
name|openIt
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"Error"
operator|+
name|args
index|[
literal|0
index|]
operator|+
literal|" is not a valid file or is not readable"
argument_list|)
expr_stmt|;
comment|//JOptionPane...
block|}
block|}
else|else
block|{
comment|//no arguments (this will be for later and other command line switches)
comment|// ignore..
block|}
block|}
block|}
end_class

end_unit

