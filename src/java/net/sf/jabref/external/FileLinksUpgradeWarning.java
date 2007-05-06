begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|imports
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
name|imports
operator|.
name|PostOpenAction
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
name|BasePanel
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

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|Util
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
name|GUIGlobals
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|*
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|ActionListener
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|ActionEvent
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

begin_comment
comment|/**  * This class defines the warning that can be offered when opening a pre-2.3  * JabRef file into a later version. This warning mentions the new external file  * link system in this version of JabRef, and offers to:  *  * * upgrade old-style PDF/PS links into the "file" field  * * modify General fields to show "file" instead of "pdf" / "ps"  * * modify table column settings to show "file" instead of "pdf" / "ps"  */
end_comment

begin_class
DECL|class|FileLinksUpgradeWarning
specifier|public
class|class
name|FileLinksUpgradeWarning
implements|implements
name|PostOpenAction
block|{
comment|/**      * This method should be performed if the major/minor versions recorded in the ParserResult      * are less than or equal to 2.2.      * @param pr      * @return true if the file was written by a jabref version<=2.2      */
DECL|method|isActionNecessary (ParserResult pr)
specifier|public
name|boolean
name|isActionNecessary
parameter_list|(
name|ParserResult
name|pr
parameter_list|)
block|{
if|if
condition|(
name|pr
operator|.
name|getJabrefMajorVersion
argument_list|()
operator|<
literal|0
condition|)
return|return
literal|false
return|;
comment|// non-JabRef file
if|if
condition|(
name|pr
operator|.
name|getJabrefMajorVersion
argument_list|()
operator|<
literal|2
condition|)
return|return
literal|true
return|;
comment|// old
if|if
condition|(
name|pr
operator|.
name|getJabrefMajorVersion
argument_list|()
operator|>
literal|2
condition|)
return|return
literal|false
return|;
comment|// wow, did we ever reach version 3?
return|return
operator|(
name|pr
operator|.
name|getJabrefMinorVersion
argument_list|()
operator|<=
literal|2
operator|)
return|;
block|}
comment|/**      * This method presents a dialog box explaining and offering to make the      * changes. If the user confirms, the changes are performed.      * @param panel      * @param pr      */
DECL|method|performAction (BasePanel panel, ParserResult pr)
specifier|public
name|void
name|performAction
parameter_list|(
name|BasePanel
name|panel
parameter_list|,
name|ParserResult
name|pr
parameter_list|)
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|(
literal|"<html>"
argument_list|)
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"This database was written using an older version of JabRef."
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"</html>"
argument_list|)
expr_stmt|;
name|int
name|answer
init|=
name|JOptionPane
operator|.
name|showConfirmDialog
argument_list|(
name|panel
operator|.
name|frame
argument_list|()
argument_list|,
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|answer
operator|==
name|JOptionPane
operator|.
name|OK_OPTION
condition|)
name|makeChanges
argument_list|(
name|panel
argument_list|,
name|pr
argument_list|)
expr_stmt|;
block|}
comment|/**      * This method performs the actual changes.      * @param panel      * @param pr      */
DECL|method|makeChanges (BasePanel panel, ParserResult pr)
specifier|public
name|void
name|makeChanges
parameter_list|(
name|BasePanel
name|panel
parameter_list|,
name|ParserResult
name|pr
parameter_list|)
block|{
comment|// Update file links links in the database:
name|Util
operator|.
name|upgradePdfPsToFile
argument_list|(
name|pr
operator|.
name|getDatabase
argument_list|()
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"pdf"
block|,
literal|"ps"
block|}
argument_list|)
expr_stmt|;
comment|// Exchange table columns:
name|Globals
operator|.
name|prefs
operator|.
name|putBoolean
argument_list|(
literal|"pdfColumn"
argument_list|,
name|Boolean
operator|.
name|FALSE
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|putBoolean
argument_list|(
literal|"fileColumn"
argument_list|,
name|Boolean
operator|.
name|TRUE
argument_list|)
expr_stmt|;
comment|// Modify General fields:
name|String
name|genF
init|=
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"generalFields"
argument_list|)
decl_stmt|;
name|Pattern
name|p1
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"\\bpdf\\b"
argument_list|)
decl_stmt|;
name|Pattern
name|p2
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"\\bps\\b"
argument_list|)
decl_stmt|;
name|boolean
name|mp1
init|=
name|p1
operator|.
name|matcher
argument_list|(
name|genF
argument_list|)
operator|.
name|matches
argument_list|()
decl_stmt|;
name|boolean
name|mp2
init|=
name|p2
operator|.
name|matcher
argument_list|(
name|genF
argument_list|)
operator|.
name|matches
argument_list|()
decl_stmt|;
comment|// Unfinished...
comment|/*if (mp1&& mp2) {             genF = genF.replaceAll("\\bpdf\\b", GUIGlobals.FILE_FIELD);             genF = genF.replaceAll("\\bps\\b", "");         }*/
block|}
block|}
end_class

end_unit

