begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2016 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.logic.openoffice
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|openoffice
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

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileNotFoundException
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
name|nio
operator|.
name|charset
operator|.
name|Charset
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

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
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Objects
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
name|logic
operator|.
name|journals
operator|.
name|JournalAbbreviationLoader
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
name|preferences
operator|.
name|JabRefPreferences
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
import|;
end_import

begin_class
DECL|class|StyleLoader
specifier|public
class|class
name|StyleLoader
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|StyleLoader
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|DEFAULT_AUTHORYEAR_STYLE_PATH
specifier|public
specifier|static
specifier|final
name|String
name|DEFAULT_AUTHORYEAR_STYLE_PATH
init|=
literal|"/resource/openoffice/default_authoryear.jstyle"
decl_stmt|;
DECL|field|DEFAULT_NUMERICAL_STYLE_PATH
specifier|public
specifier|static
specifier|final
name|String
name|DEFAULT_NUMERICAL_STYLE_PATH
init|=
literal|"/resource/openoffice/default_numerical.jstyle"
decl_stmt|;
comment|// All internal styles
DECL|field|internalStyleFiles
specifier|private
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|internalStyleFiles
init|=
name|Arrays
operator|.
name|asList
argument_list|(
name|DEFAULT_AUTHORYEAR_STYLE_PATH
argument_list|,
name|DEFAULT_NUMERICAL_STYLE_PATH
argument_list|)
decl_stmt|;
DECL|field|journalAbbreviationLoader
specifier|private
specifier|final
name|JournalAbbreviationLoader
name|journalAbbreviationLoader
decl_stmt|;
DECL|field|preferences
specifier|private
specifier|final
name|OpenOfficePreferences
name|preferences
decl_stmt|;
DECL|field|encoding
specifier|private
specifier|final
name|Charset
name|encoding
decl_stmt|;
DECL|field|jabrefPreferences
specifier|private
specifier|final
name|JabRefPreferences
name|jabrefPreferences
decl_stmt|;
comment|// Lists of the internal
comment|// and external styles
DECL|field|internalStyles
specifier|private
specifier|final
name|List
argument_list|<
name|OOBibStyle
argument_list|>
name|internalStyles
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|externalStyles
specifier|private
specifier|final
name|List
argument_list|<
name|OOBibStyle
argument_list|>
name|externalStyles
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
DECL|method|StyleLoader (OpenOfficePreferences preferences, JabRefPreferences jabrefPreferences, JournalAbbreviationLoader journalAbbreviationLoader, Charset encoding)
specifier|public
name|StyleLoader
parameter_list|(
name|OpenOfficePreferences
name|preferences
parameter_list|,
name|JabRefPreferences
name|jabrefPreferences
parameter_list|,
name|JournalAbbreviationLoader
name|journalAbbreviationLoader
parameter_list|,
name|Charset
name|encoding
parameter_list|)
block|{
name|this
operator|.
name|journalAbbreviationLoader
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|journalAbbreviationLoader
argument_list|)
expr_stmt|;
name|this
operator|.
name|preferences
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|preferences
argument_list|)
expr_stmt|;
name|this
operator|.
name|jabrefPreferences
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|jabrefPreferences
argument_list|)
expr_stmt|;
name|this
operator|.
name|encoding
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|encoding
argument_list|)
expr_stmt|;
name|loadInternalStyles
argument_list|()
expr_stmt|;
name|loadExternalStyles
argument_list|()
expr_stmt|;
block|}
DECL|method|getStyles ()
specifier|public
name|List
argument_list|<
name|OOBibStyle
argument_list|>
name|getStyles
parameter_list|()
block|{
name|List
argument_list|<
name|OOBibStyle
argument_list|>
name|result
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
name|internalStyles
argument_list|)
decl_stmt|;
name|result
operator|.
name|addAll
argument_list|(
name|externalStyles
argument_list|)
expr_stmt|;
return|return
name|result
return|;
block|}
comment|/**      * Adds the given style to the list of styles      * @param filename The filename of the style      * @return True if the added style is valid, false otherwise      */
DECL|method|addStyleIfValid (String filename)
specifier|public
name|boolean
name|addStyleIfValid
parameter_list|(
name|String
name|filename
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|filename
argument_list|)
expr_stmt|;
try|try
block|{
name|OOBibStyle
name|newStyle
init|=
operator|new
name|OOBibStyle
argument_list|(
operator|new
name|File
argument_list|(
name|filename
argument_list|)
argument_list|,
name|jabrefPreferences
argument_list|,
name|journalAbbreviationLoader
argument_list|,
name|encoding
argument_list|)
decl_stmt|;
if|if
condition|(
name|externalStyles
operator|.
name|contains
argument_list|(
name|newStyle
argument_list|)
condition|)
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"External style file "
operator|+
name|filename
operator|+
literal|" already existing."
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|newStyle
operator|.
name|isValid
argument_list|()
condition|)
block|{
name|externalStyles
operator|.
name|add
argument_list|(
name|newStyle
argument_list|)
expr_stmt|;
name|storeExternalStyles
argument_list|()
expr_stmt|;
return|return
literal|true
return|;
block|}
else|else
block|{
name|LOGGER
operator|.
name|error
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Style with filename %s is invalid"
argument_list|,
name|filename
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|FileNotFoundException
name|e
parameter_list|)
block|{
comment|// The file couldn't be found... should we tell anyone?
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Cannot find external style file "
operator|+
name|filename
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Problem reading external style file "
operator|+
name|filename
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
return|return
literal|false
return|;
block|}
DECL|method|loadExternalStyles ()
specifier|private
name|void
name|loadExternalStyles
parameter_list|()
block|{
name|externalStyles
operator|.
name|clear
argument_list|()
expr_stmt|;
comment|// Read external lists
name|List
argument_list|<
name|String
argument_list|>
name|lists
init|=
name|preferences
operator|.
name|getExternalStyles
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|filename
range|:
name|lists
control|)
block|{
try|try
block|{
name|OOBibStyle
name|style
init|=
operator|new
name|OOBibStyle
argument_list|(
operator|new
name|File
argument_list|(
name|filename
argument_list|)
argument_list|,
name|jabrefPreferences
argument_list|,
name|journalAbbreviationLoader
argument_list|,
name|encoding
argument_list|)
decl_stmt|;
if|if
condition|(
name|style
operator|.
name|isValid
argument_list|()
condition|)
block|{
comment|//Problem!
name|externalStyles
operator|.
name|add
argument_list|(
name|style
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|LOGGER
operator|.
name|error
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Style with filename %s is invalid"
argument_list|,
name|filename
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|FileNotFoundException
name|e
parameter_list|)
block|{
comment|// The file couldn't be found... should we tell anyone?
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Cannot find external style file "
operator|+
name|filename
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Problem reading external style file "
operator|+
name|filename
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|method|loadInternalStyles ()
specifier|private
name|void
name|loadInternalStyles
parameter_list|()
block|{
name|internalStyles
operator|.
name|clear
argument_list|()
expr_stmt|;
for|for
control|(
name|String
name|filename
range|:
name|internalStyleFiles
control|)
block|{
try|try
block|{
name|internalStyles
operator|.
name|add
argument_list|(
operator|new
name|OOBibStyle
argument_list|(
name|filename
argument_list|,
name|jabrefPreferences
argument_list|,
name|journalAbbreviationLoader
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Problem reading internal style file "
operator|+
name|filename
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|method|storeExternalStyles ()
specifier|private
name|void
name|storeExternalStyles
parameter_list|()
block|{
name|List
argument_list|<
name|String
argument_list|>
name|filenames
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
name|externalStyles
operator|.
name|size
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|OOBibStyle
name|style
range|:
name|externalStyles
control|)
block|{
name|filenames
operator|.
name|add
argument_list|(
name|style
operator|.
name|getPath
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|preferences
operator|.
name|setExternalStyles
argument_list|(
name|filenames
argument_list|)
expr_stmt|;
block|}
DECL|method|removeStyle (OOBibStyle style)
specifier|public
name|boolean
name|removeStyle
parameter_list|(
name|OOBibStyle
name|style
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|style
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|style
operator|.
name|isFromResource
argument_list|()
condition|)
block|{
name|boolean
name|result
init|=
name|externalStyles
operator|.
name|remove
argument_list|(
name|style
argument_list|)
decl_stmt|;
name|storeExternalStyles
argument_list|()
expr_stmt|;
return|return
name|result
return|;
block|}
return|return
literal|false
return|;
block|}
DECL|method|getUsedStyle ()
specifier|public
name|OOBibStyle
name|getUsedStyle
parameter_list|()
block|{
name|String
name|filename
init|=
name|preferences
operator|.
name|getCurrentStyle
argument_list|()
decl_stmt|;
if|if
condition|(
name|filename
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|OOBibStyle
name|style
range|:
name|getStyles
argument_list|()
control|)
block|{
if|if
condition|(
name|filename
operator|.
name|equals
argument_list|(
name|style
operator|.
name|getPath
argument_list|()
argument_list|)
condition|)
block|{
return|return
name|style
return|;
block|}
block|}
block|}
comment|// Pick the first internal
name|preferences
operator|.
name|setCurrentStyle
argument_list|(
name|internalStyles
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getPath
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|internalStyles
operator|.
name|get
argument_list|(
literal|0
argument_list|)
return|;
block|}
block|}
end_class

end_unit

