begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.gui
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
package|;
end_package

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
name|javax
operator|.
name|swing
operator|.
name|JOptionPane
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
name|importer
operator|.
name|ParserResult
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

begin_class
DECL|class|ParserResultWarningDialog
specifier|public
class|class
name|ParserResultWarningDialog
block|{
comment|/**      * Shows a dialog with the warnings from an import or open of a file      *      * @param parserResult - ParserResult for the current import/open      * @param jabRefFrame - the JabRefFrame      */
DECL|method|showParserResultWarningDialog (ParserResult parserResult, JabRefFrame jabRefFrame)
specifier|public
specifier|static
name|void
name|showParserResultWarningDialog
parameter_list|(
name|ParserResult
name|parserResult
parameter_list|,
name|JabRefFrame
name|jabRefFrame
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|parserResult
argument_list|)
expr_stmt|;
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|jabRefFrame
argument_list|)
expr_stmt|;
name|showParserResultWarningDialog
argument_list|(
name|parserResult
argument_list|,
name|jabRefFrame
argument_list|,
name|Integer
operator|.
name|MAX_VALUE
argument_list|,
operator|-
literal|1
argument_list|)
expr_stmt|;
block|}
comment|/**      * Shows a dialog with the warnings from an import or open of a file      *      * @param parserResult - ParserResult for the current import/open      * @param jabRefFrame - the JabRefFrame      * @param maxWarnings - Maximum number of warnings to display      * @param dataBaseNumber - Database tab number to activate when showing the warning dialog      */
DECL|method|showParserResultWarningDialog (ParserResult parserResult, JabRefFrame jabRefFrame, int maxWarnings, int dataBaseNumber)
specifier|public
specifier|static
name|void
name|showParserResultWarningDialog
parameter_list|(
name|ParserResult
name|parserResult
parameter_list|,
name|JabRefFrame
name|jabRefFrame
parameter_list|,
name|int
name|maxWarnings
parameter_list|,
name|int
name|dataBaseNumber
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|parserResult
argument_list|)
expr_stmt|;
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|jabRefFrame
argument_list|)
expr_stmt|;
if|if
condition|(
name|parserResult
operator|.
name|hasWarnings
argument_list|()
condition|)
block|{
if|if
condition|(
name|dataBaseNumber
operator|>=
literal|0
condition|)
block|{
name|jabRefFrame
operator|.
name|showBasePanelAt
argument_list|(
name|dataBaseNumber
argument_list|)
expr_stmt|;
block|}
comment|// Generate string with warning texts
name|List
argument_list|<
name|String
argument_list|>
name|warnings
init|=
name|parserResult
operator|.
name|warnings
argument_list|()
decl_stmt|;
name|StringBuilder
name|dialogContent
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|Math
operator|.
name|min
argument_list|(
name|maxWarnings
argument_list|,
name|warnings
operator|.
name|size
argument_list|()
argument_list|)
condition|;
name|j
operator|++
control|)
block|{
name|dialogContent
operator|.
name|append
argument_list|(
name|j
operator|+
literal|1
argument_list|)
operator|.
name|append
argument_list|(
literal|". "
argument_list|)
operator|.
name|append
argument_list|(
name|warnings
operator|.
name|get
argument_list|(
name|j
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|"\n"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|warnings
operator|.
name|size
argument_list|()
operator|>
name|maxWarnings
condition|)
block|{
name|dialogContent
operator|.
name|append
argument_list|(
literal|"... "
argument_list|)
expr_stmt|;
name|dialogContent
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 warnings"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|warnings
operator|.
name|size
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|dialogContent
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|dialogContent
operator|.
name|deleteCharAt
argument_list|(
name|dialogContent
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
block|}
comment|// Generate dialog title
name|String
name|dialogTitle
decl_stmt|;
if|if
condition|(
name|dataBaseNumber
operator|<
literal|0
condition|)
block|{
name|dialogTitle
operator|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Warnings"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|dialogTitle
operator|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Warnings"
argument_list|)
operator|+
literal|" ("
operator|+
name|parserResult
operator|.
name|getFile
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|")"
expr_stmt|;
block|}
comment|// Comment from the old code:
comment|//
comment|// Note to self or to someone else: The following line causes an
comment|// ArrayIndexOutOfBoundsException in situations with a large number of
comment|// warnings; approx. 5000 for the database I opened when I observed the problem
comment|// (duplicate key warnings). I don't think this is a big problem for normal situations,
comment|// and it may possibly be a bug in the Swing code.
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|jabRefFrame
argument_list|,
name|dialogContent
operator|.
name|toString
argument_list|()
argument_list|,
name|dialogTitle
argument_list|,
name|JOptionPane
operator|.
name|WARNING_MESSAGE
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

