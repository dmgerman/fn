begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2003 Nizar N. Batada, Morten O. Alver  All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  */
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
name|*
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Matcher
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JFileChooser
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|logging
operator|.
name|*
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

begin_class
DECL|class|Globals
specifier|public
class|class
name|Globals
block|{
DECL|field|resourcePrefix
specifier|private
specifier|static
name|String
name|resourcePrefix
init|=
literal|"resource/JabRef"
decl_stmt|;
DECL|field|logfile
specifier|private
specifier|static
name|String
name|logfile
init|=
literal|"jabref.log"
decl_stmt|;
DECL|field|messages
specifier|public
specifier|static
name|ResourceBundle
name|messages
decl_stmt|;
DECL|method|logger (String s)
specifier|public
specifier|static
name|void
name|logger
parameter_list|(
name|String
name|s
parameter_list|)
block|{
name|Logger
operator|.
name|global
operator|.
name|info
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
DECL|method|turnOffLogging ()
specifier|public
specifier|static
name|void
name|turnOffLogging
parameter_list|()
block|{
comment|// only log exceptions
name|Logger
operator|.
name|global
operator|.
name|setLevel
argument_list|(
name|java
operator|.
name|util
operator|.
name|logging
operator|.
name|Level
operator|.
name|SEVERE
argument_list|)
expr_stmt|;
block|}
comment|// should be only called ones
DECL|method|turnOnConsoleLogging ()
specifier|public
specifier|static
name|void
name|turnOnConsoleLogging
parameter_list|()
block|{
name|Logger
operator|.
name|global
operator|.
name|addHandler
argument_list|(
operator|new
name|java
operator|.
name|util
operator|.
name|logging
operator|.
name|ConsoleHandler
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|turnOnFileLogging ()
specifier|public
specifier|static
name|void
name|turnOnFileLogging
parameter_list|()
block|{
name|Logger
operator|.
name|global
operator|.
name|setLevel
argument_list|(
name|java
operator|.
name|util
operator|.
name|logging
operator|.
name|Level
operator|.
name|ALL
argument_list|)
expr_stmt|;
name|java
operator|.
name|util
operator|.
name|logging
operator|.
name|Handler
name|handler
decl_stmt|;
try|try
block|{
name|handler
operator|=
operator|new
name|FileHandler
argument_list|(
name|logfile
argument_list|)
expr_stmt|;
comment|// this will overwrite
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
comment|//can't open log file so use console
name|handler
operator|=
operator|new
name|ConsoleHandler
argument_list|()
expr_stmt|;
block|}
name|Logger
operator|.
name|global
operator|.
name|addHandler
argument_list|(
name|handler
argument_list|)
expr_stmt|;
name|handler
operator|.
name|setFilter
argument_list|(
operator|new
name|Filter
argument_list|()
block|{
comment|// select what gets logged
specifier|public
name|boolean
name|isLoggable
parameter_list|(
name|LogRecord
name|record
parameter_list|)
block|{
return|return
literal|true
return|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
comment|/**      * String constants.      */
specifier|public
specifier|static
specifier|final
name|String
DECL|field|KEY_FIELD
name|KEY_FIELD
init|=
literal|"bibtexkey"
decl_stmt|,
DECL|field|SEARCH
name|SEARCH
init|=
literal|"search"
decl_stmt|,
DECL|field|GROUPSEARCH
name|GROUPSEARCH
init|=
literal|"groupsearch"
decl_stmt|,
comment|// Using this when I have no database open when I read
comment|// non bibtex file formats (used byte ImportFormatReader.java
DECL|field|DEFAULT_BIBTEXENTRY_ID
name|DEFAULT_BIBTEXENTRY_ID
init|=
literal|"__ID"
decl_stmt|;
DECL|method|setLanguage (String language, String country)
specifier|public
specifier|static
name|void
name|setLanguage
parameter_list|(
name|String
name|language
parameter_list|,
name|String
name|country
parameter_list|)
block|{
name|messages
operator|=
name|ResourceBundle
operator|.
name|getBundle
argument_list|(
name|resourcePrefix
argument_list|,
operator|new
name|Locale
argument_list|(
name|language
argument_list|,
name|country
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|lang (String key)
specifier|public
specifier|static
name|String
name|lang
parameter_list|(
name|String
name|key
parameter_list|)
block|{
name|String
name|translation
decl_stmt|;
try|try
block|{
name|translation
operator|=
name|Globals
operator|.
name|messages
operator|.
name|getString
argument_list|(
name|key
operator|.
name|replaceAll
argument_list|(
literal|" "
argument_list|,
literal|"_"
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|MissingResourceException
name|ex
parameter_list|)
block|{
name|translation
operator|=
name|key
expr_stmt|;
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"Warning: could not get translation for \""
operator|+
name|key
operator|+
literal|"\""
argument_list|)
expr_stmt|;
block|}
return|return
name|translation
operator|.
name|replaceAll
argument_list|(
literal|"_"
argument_list|,
literal|" "
argument_list|)
return|;
block|}
comment|//============================================================
comment|// this is incomplete...i need to add all the types here
comment|//============================================================
DECL|method|getEntryType (String type)
specifier|static
name|BibtexEntryType
name|getEntryType
parameter_list|(
name|String
name|type
parameter_list|)
block|{
comment|// decide which entryType object to return
if|if
condition|(
name|type
operator|.
name|equals
argument_list|(
literal|"article"
argument_list|)
condition|)
return|return
name|BibtexEntryType
operator|.
name|ARTICLE
return|;
elseif|else
if|if
condition|(
name|type
operator|.
name|equals
argument_list|(
literal|"book"
argument_list|)
condition|)
return|return
name|BibtexEntryType
operator|.
name|BOOK
return|;
elseif|else
if|if
condition|(
name|type
operator|.
name|equals
argument_list|(
literal|"inproceedings"
argument_list|)
condition|)
return|return
name|BibtexEntryType
operator|.
name|INPROCEEDINGS
return|;
else|else
comment|//if(type.equals("other"))
return|return
name|BibtexEntryType
operator|.
name|OTHER
return|;
block|}
comment|//========================================================
comment|// lot of abreviations in medline
comment|// PKC etc convert to {PKC} ...
comment|//========================================================
DECL|field|titleCapitalPattern
specifier|static
name|Pattern
name|titleCapitalPattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"[A-Z]+"
argument_list|)
decl_stmt|;
DECL|method|putBracesAroundCapitals (String title)
specifier|static
name|String
name|putBracesAroundCapitals
parameter_list|(
name|String
name|title
parameter_list|)
block|{
name|StringBuffer
name|buf
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|Matcher
name|mcr
init|=
name|Globals
operator|.
name|titleCapitalPattern
operator|.
name|matcher
argument_list|(
name|title
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
argument_list|)
decl_stmt|;
name|boolean
name|found
init|=
literal|false
decl_stmt|;
while|while
condition|(
operator|(
name|found
operator|=
name|mcr
operator|.
name|find
argument_list|()
operator|)
condition|)
block|{
name|String
name|replaceStr
init|=
name|mcr
operator|.
name|group
argument_list|()
decl_stmt|;
name|mcr
operator|.
name|appendReplacement
argument_list|(
name|buf
argument_list|,
literal|"{"
operator|+
name|replaceStr
operator|+
literal|"}"
argument_list|)
expr_stmt|;
block|}
name|mcr
operator|.
name|appendTail
argument_list|(
name|buf
argument_list|)
expr_stmt|;
name|String
name|titleCap
init|=
name|title
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|1
argument_list|)
operator|+
name|buf
operator|.
name|toString
argument_list|()
decl_stmt|;
return|return
name|titleCap
return|;
block|}
comment|/*    public static void setupKeyBindings(JabRefPreferences prefs) { 		   	 	}*/
block|}
end_class

end_unit

