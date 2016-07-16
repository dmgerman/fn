begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2016 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.cli
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|cli
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
name|Globals
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
name|exporter
operator|.
name|ExportFormats
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
name|l10n
operator|.
name|Localization
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
name|cli
operator|.
name|CommandLine
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
name|cli
operator|.
name|DefaultParser
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
name|cli
operator|.
name|HelpFormatter
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
name|cli
operator|.
name|Option
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
name|cli
operator|.
name|Options
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
name|cli
operator|.
name|ParseException
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
DECL|class|JabRefCLI
specifier|public
class|class
name|JabRefCLI
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
name|JabRefCLI
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|leftOver
specifier|private
name|String
index|[]
name|leftOver
decl_stmt|;
DECL|field|cl
specifier|private
specifier|final
name|CommandLine
name|cl
decl_stmt|;
DECL|method|JabRefCLI (String[] args)
specifier|public
name|JabRefCLI
parameter_list|(
name|String
index|[]
name|args
parameter_list|)
block|{
name|Options
name|options
init|=
name|getOptions
argument_list|()
decl_stmt|;
try|try
block|{
name|this
operator|.
name|cl
operator|=
operator|new
name|DefaultParser
argument_list|()
operator|.
name|parse
argument_list|(
name|options
argument_list|,
name|args
argument_list|)
expr_stmt|;
name|this
operator|.
name|leftOver
operator|=
name|cl
operator|.
name|getArgs
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ParseException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Problem parsing arguments"
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|this
operator|.
name|printUsage
argument_list|()
expr_stmt|;
throw|throw
operator|new
name|RuntimeException
argument_list|()
throw|;
block|}
block|}
DECL|method|isHelp ()
specifier|public
name|boolean
name|isHelp
parameter_list|()
block|{
return|return
name|cl
operator|.
name|hasOption
argument_list|(
literal|"help"
argument_list|)
return|;
block|}
DECL|method|isShowVersion ()
specifier|public
name|boolean
name|isShowVersion
parameter_list|()
block|{
return|return
name|cl
operator|.
name|hasOption
argument_list|(
literal|"version"
argument_list|)
return|;
block|}
DECL|method|isBlank ()
specifier|public
name|boolean
name|isBlank
parameter_list|()
block|{
return|return
name|cl
operator|.
name|hasOption
argument_list|(
literal|"blank"
argument_list|)
return|;
block|}
DECL|method|isLoadSession ()
specifier|public
name|boolean
name|isLoadSession
parameter_list|()
block|{
return|return
name|cl
operator|.
name|hasOption
argument_list|(
literal|"loads"
argument_list|)
return|;
block|}
DECL|method|isDisableGui ()
specifier|public
name|boolean
name|isDisableGui
parameter_list|()
block|{
return|return
name|cl
operator|.
name|hasOption
argument_list|(
literal|"nogui"
argument_list|)
return|;
block|}
DECL|method|isPreferencesExport ()
specifier|public
name|boolean
name|isPreferencesExport
parameter_list|()
block|{
return|return
name|cl
operator|.
name|hasOption
argument_list|(
literal|"prexp"
argument_list|)
return|;
block|}
DECL|method|getPreferencesExport ()
specifier|public
name|String
name|getPreferencesExport
parameter_list|()
block|{
return|return
name|cl
operator|.
name|getOptionValue
argument_list|(
literal|"prexp"
argument_list|,
literal|"jabref_prefs.xml"
argument_list|)
return|;
block|}
DECL|method|isPreferencesImport ()
specifier|public
name|boolean
name|isPreferencesImport
parameter_list|()
block|{
return|return
name|cl
operator|.
name|hasOption
argument_list|(
literal|"primp"
argument_list|)
return|;
block|}
DECL|method|getPreferencesImport ()
specifier|public
name|String
name|getPreferencesImport
parameter_list|()
block|{
return|return
name|cl
operator|.
name|getOptionValue
argument_list|(
literal|"primp"
argument_list|,
literal|"jabref_prefs.xml"
argument_list|)
return|;
block|}
DECL|method|isPreferencesReset ()
specifier|public
name|boolean
name|isPreferencesReset
parameter_list|()
block|{
return|return
name|cl
operator|.
name|hasOption
argument_list|(
literal|"prdef"
argument_list|)
return|;
block|}
DECL|method|getPreferencesReset ()
specifier|public
name|String
name|getPreferencesReset
parameter_list|()
block|{
return|return
name|cl
operator|.
name|getOptionValue
argument_list|(
literal|"prdef"
argument_list|)
return|;
block|}
DECL|method|isFileExport ()
specifier|public
name|boolean
name|isFileExport
parameter_list|()
block|{
return|return
name|cl
operator|.
name|hasOption
argument_list|(
literal|"output"
argument_list|)
return|;
block|}
DECL|method|getFileExport ()
specifier|public
name|String
name|getFileExport
parameter_list|()
block|{
return|return
name|cl
operator|.
name|getOptionValue
argument_list|(
literal|"output"
argument_list|)
return|;
block|}
DECL|method|isFileImport ()
specifier|public
name|boolean
name|isFileImport
parameter_list|()
block|{
return|return
name|cl
operator|.
name|hasOption
argument_list|(
literal|"import"
argument_list|)
return|;
block|}
DECL|method|getFileImport ()
specifier|public
name|String
name|getFileImport
parameter_list|()
block|{
return|return
name|cl
operator|.
name|getOptionValue
argument_list|(
literal|"import"
argument_list|)
return|;
block|}
DECL|method|isAuxImport ()
specifier|public
name|boolean
name|isAuxImport
parameter_list|()
block|{
return|return
name|cl
operator|.
name|hasOption
argument_list|(
literal|"aux"
argument_list|)
return|;
block|}
DECL|method|getAuxImport ()
specifier|public
name|String
name|getAuxImport
parameter_list|()
block|{
return|return
name|cl
operator|.
name|getOptionValue
argument_list|(
literal|"aux"
argument_list|)
return|;
block|}
DECL|method|isImportToOpenBase ()
specifier|public
name|boolean
name|isImportToOpenBase
parameter_list|()
block|{
return|return
name|cl
operator|.
name|hasOption
argument_list|(
literal|"importToOpen"
argument_list|)
return|;
block|}
DECL|method|getImportToOpenBase ()
specifier|public
name|String
name|getImportToOpenBase
parameter_list|()
block|{
return|return
name|cl
operator|.
name|getOptionValue
argument_list|(
literal|"importToOpen"
argument_list|)
return|;
block|}
DECL|method|isDebugLogging ()
specifier|public
name|boolean
name|isDebugLogging
parameter_list|()
block|{
return|return
name|cl
operator|.
name|hasOption
argument_list|(
literal|"debug"
argument_list|)
return|;
block|}
DECL|method|isFetcherEngine ()
specifier|public
name|boolean
name|isFetcherEngine
parameter_list|()
block|{
return|return
name|cl
operator|.
name|hasOption
argument_list|(
literal|"fetch"
argument_list|)
return|;
block|}
DECL|method|getFetcherEngine ()
specifier|public
name|String
name|getFetcherEngine
parameter_list|()
block|{
return|return
name|cl
operator|.
name|getOptionValue
argument_list|(
literal|"fetch"
argument_list|)
return|;
block|}
DECL|method|isExportMatches ()
specifier|public
name|boolean
name|isExportMatches
parameter_list|()
block|{
return|return
name|cl
operator|.
name|hasOption
argument_list|(
literal|"exportMatches"
argument_list|)
return|;
block|}
DECL|method|getExportMatches ()
specifier|public
name|String
name|getExportMatches
parameter_list|()
block|{
return|return
name|cl
operator|.
name|getOptionValue
argument_list|(
literal|"exportMatches"
argument_list|)
return|;
block|}
DECL|method|isGenerateBibtexKeys ()
specifier|public
name|boolean
name|isGenerateBibtexKeys
parameter_list|()
block|{
return|return
name|cl
operator|.
name|hasOption
argument_list|(
literal|"generateBibtexKeys"
argument_list|)
return|;
block|}
DECL|method|isAutomaticallySetFileLinks ()
specifier|public
name|boolean
name|isAutomaticallySetFileLinks
parameter_list|()
block|{
return|return
name|cl
operator|.
name|hasOption
argument_list|(
literal|"automaticallySetFileLinks"
argument_list|)
return|;
block|}
DECL|method|getOptions ()
specifier|private
name|Options
name|getOptions
parameter_list|()
block|{
name|Options
name|options
init|=
operator|new
name|Options
argument_list|()
decl_stmt|;
comment|// boolean options
name|options
operator|.
name|addOption
argument_list|(
literal|"v"
argument_list|,
literal|"version"
argument_list|,
literal|false
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Display version"
argument_list|)
argument_list|)
expr_stmt|;
name|options
operator|.
name|addOption
argument_list|(
literal|"n"
argument_list|,
literal|"nogui"
argument_list|,
literal|false
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"No GUI. Only process command line options."
argument_list|)
argument_list|)
expr_stmt|;
name|options
operator|.
name|addOption
argument_list|(
literal|"h"
argument_list|,
literal|"help"
argument_list|,
literal|false
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Display help on command line options"
argument_list|)
argument_list|)
expr_stmt|;
name|options
operator|.
name|addOption
argument_list|(
literal|"b"
argument_list|,
literal|"blank"
argument_list|,
literal|false
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Do not open any files at startup"
argument_list|)
argument_list|)
expr_stmt|;
name|options
operator|.
name|addOption
argument_list|(
literal|null
argument_list|,
literal|"debug"
argument_list|,
literal|false
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Show debug level messages"
argument_list|)
argument_list|)
expr_stmt|;
name|options
operator|.
name|addOption
argument_list|(
name|Option
operator|.
name|builder
argument_list|(
literal|"i"
argument_list|)
operator|.
name|longOpt
argument_list|(
literal|"import"
argument_list|)
operator|.
name|desc
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"%s: %s[,import format]"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Import file"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"filename"
argument_list|)
argument_list|)
argument_list|)
operator|.
name|hasArg
argument_list|()
operator|.
name|argName
argument_list|(
literal|"FILE"
argument_list|)
operator|.
name|build
argument_list|()
argument_list|)
expr_stmt|;
name|options
operator|.
name|addOption
argument_list|(
name|Option
operator|.
name|builder
argument_list|(
literal|"o"
argument_list|)
operator|.
name|longOpt
argument_list|(
literal|"output"
argument_list|)
operator|.
name|desc
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"%s: %s[,export format]"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Output or export file"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"filename"
argument_list|)
argument_list|)
argument_list|)
operator|.
name|hasArg
argument_list|()
operator|.
name|argName
argument_list|(
literal|"FILE"
argument_list|)
operator|.
name|build
argument_list|()
argument_list|)
expr_stmt|;
name|options
operator|.
name|addOption
argument_list|(
name|Option
operator|.
name|builder
argument_list|(
literal|"x"
argument_list|)
operator|.
name|longOpt
argument_list|(
literal|"prexp"
argument_list|)
operator|.
name|desc
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Export preferences to file"
argument_list|)
argument_list|)
operator|.
name|hasArg
argument_list|()
operator|.
name|argName
argument_list|(
literal|"FILE"
argument_list|)
operator|.
name|build
argument_list|()
argument_list|)
expr_stmt|;
name|options
operator|.
name|addOption
argument_list|(
name|Option
operator|.
name|builder
argument_list|(
literal|"p"
argument_list|)
operator|.
name|longOpt
argument_list|(
literal|"primp"
argument_list|)
operator|.
name|desc
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Import preferences from file"
argument_list|)
argument_list|)
operator|.
name|hasArg
argument_list|()
operator|.
name|argName
argument_list|(
literal|"FILE"
argument_list|)
operator|.
name|build
argument_list|()
argument_list|)
expr_stmt|;
name|options
operator|.
name|addOption
argument_list|(
name|Option
operator|.
name|builder
argument_list|(
literal|"d"
argument_list|)
operator|.
name|longOpt
argument_list|(
literal|"prdef"
argument_list|)
operator|.
name|desc
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Reset preferences (key1,key2,... or 'all')"
argument_list|)
argument_list|)
operator|.
name|hasArg
argument_list|()
operator|.
name|argName
argument_list|(
literal|"FILE"
argument_list|)
operator|.
name|build
argument_list|()
argument_list|)
expr_stmt|;
name|options
operator|.
name|addOption
argument_list|(
name|Option
operator|.
name|builder
argument_list|(
literal|"a"
argument_list|)
operator|.
name|longOpt
argument_list|(
literal|"aux"
argument_list|)
operator|.
name|desc
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"%s: %s[.aux],%s[.bib]"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Subdatabase from aux"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"file"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"new"
argument_list|)
argument_list|)
argument_list|)
operator|.
name|hasArg
argument_list|()
operator|.
name|argName
argument_list|(
literal|"FILE"
argument_list|)
operator|.
name|build
argument_list|()
argument_list|)
expr_stmt|;
name|options
operator|.
name|addOption
argument_list|(
name|Option
operator|.
name|builder
argument_list|()
operator|.
name|longOpt
argument_list|(
literal|"importToOpen"
argument_list|)
operator|.
name|desc
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Import to open tab"
argument_list|)
argument_list|)
operator|.
name|hasArg
argument_list|()
operator|.
name|argName
argument_list|(
literal|"FILE"
argument_list|)
operator|.
name|build
argument_list|()
argument_list|)
expr_stmt|;
name|options
operator|.
name|addOption
argument_list|(
name|Option
operator|.
name|builder
argument_list|(
literal|"f"
argument_list|)
operator|.
name|longOpt
argument_list|(
literal|"fetch"
argument_list|)
operator|.
name|desc
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Run Fetcher, e.g. \"--fetch=Medline:cancer\""
argument_list|)
argument_list|)
operator|.
name|hasArg
argument_list|()
operator|.
name|argName
argument_list|(
literal|"FILE"
argument_list|)
operator|.
name|build
argument_list|()
argument_list|)
expr_stmt|;
name|options
operator|.
name|addOption
argument_list|(
name|Option
operator|.
name|builder
argument_list|(
literal|"m"
argument_list|)
operator|.
name|longOpt
argument_list|(
literal|"exportMatches"
argument_list|)
operator|.
name|desc
argument_list|(
name|JabRefCLI
operator|.
name|getExportMatchesSyntax
argument_list|()
argument_list|)
operator|.
name|hasArg
argument_list|()
operator|.
name|argName
argument_list|(
literal|"FILE"
argument_list|)
operator|.
name|build
argument_list|()
argument_list|)
expr_stmt|;
name|options
operator|.
name|addOption
argument_list|(
name|Option
operator|.
name|builder
argument_list|(
literal|"g"
argument_list|)
operator|.
name|longOpt
argument_list|(
literal|"generateBibtexKeys"
argument_list|)
operator|.
name|desc
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Regenerate all keys for the entries in a BibTeX file"
argument_list|)
argument_list|)
operator|.
name|build
argument_list|()
argument_list|)
expr_stmt|;
name|options
operator|.
name|addOption
argument_list|(
name|Option
operator|.
name|builder
argument_list|(
literal|"asfl"
argument_list|)
operator|.
name|longOpt
argument_list|(
literal|"automaticallySetFileLinks"
argument_list|)
operator|.
name|desc
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Automatically set file links"
argument_list|)
argument_list|)
operator|.
name|build
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|options
return|;
block|}
DECL|method|displayVersion ()
specifier|public
name|void
name|displayVersion
parameter_list|()
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|getVersionInfo
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|printUsage ()
specifier|public
name|void
name|printUsage
parameter_list|()
block|{
name|String
name|header
init|=
literal|""
decl_stmt|;
name|String
name|importFormats
init|=
name|Globals
operator|.
name|IMPORT_FORMAT_READER
operator|.
name|getImportFormatList
argument_list|()
decl_stmt|;
name|String
name|importFormatsList
init|=
name|String
operator|.
name|format
argument_list|(
literal|"%s:%n%s%n"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Available import formats"
argument_list|)
argument_list|,
name|importFormats
argument_list|)
decl_stmt|;
name|String
name|outFormats
init|=
name|ExportFormats
operator|.
name|getConsoleExportList
argument_list|(
literal|70
argument_list|,
literal|20
argument_list|,
literal|""
argument_list|)
decl_stmt|;
name|String
name|outFormatsList
init|=
name|String
operator|.
name|format
argument_list|(
literal|"%s: %s%n"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Available export formats"
argument_list|)
argument_list|,
name|outFormats
argument_list|)
decl_stmt|;
name|String
name|footer
init|=
literal|'\n'
operator|+
name|importFormatsList
operator|+
name|outFormatsList
operator|+
literal|"\nPlease report issues at https://github.com/JabRef/jabref/issues"
decl_stmt|;
name|HelpFormatter
name|formatter
init|=
operator|new
name|HelpFormatter
argument_list|()
decl_stmt|;
name|formatter
operator|.
name|printHelp
argument_list|(
literal|"jabref [OPTIONS] [BIBTEX_FILE]\n\nOptions:"
argument_list|,
name|header
argument_list|,
name|getOptions
argument_list|()
argument_list|,
name|footer
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
DECL|method|getVersionInfo ()
specifier|private
name|String
name|getVersionInfo
parameter_list|()
block|{
return|return
name|String
operator|.
name|format
argument_list|(
literal|"JabRef %s"
argument_list|,
name|Globals
operator|.
name|BUILD_INFO
operator|.
name|getVersion
argument_list|()
argument_list|)
return|;
block|}
DECL|method|getLeftOver ()
specifier|public
name|String
index|[]
name|getLeftOver
parameter_list|()
block|{
return|return
name|leftOver
return|;
block|}
DECL|method|getExportMatchesSyntax ()
specifier|public
specifier|static
name|String
name|getExportMatchesSyntax
parameter_list|()
block|{
return|return
name|String
operator|.
name|format
argument_list|(
literal|"[%s]searchTerm,outputFile: %s[,%s]"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"field"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"file"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"exportFormat"
argument_list|)
argument_list|)
return|;
block|}
block|}
end_class

end_unit

