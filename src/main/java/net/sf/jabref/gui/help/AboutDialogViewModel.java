begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.help
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|help
package|;
end_package

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
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|ReadOnlyStringProperty
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|ReadOnlyStringWrapper
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|fxml
operator|.
name|FXML
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|Button
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|image
operator|.
name|Image
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|image
operator|.
name|ImageView
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|stage
operator|.
name|Stage
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
name|JabRefGUI
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
name|gui
operator|.
name|ClipBoardManager
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
name|gui
operator|.
name|desktop
operator|.
name|JabRefDesktop
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
name|BuildInfo
import|;
end_import

begin_import
import|import
name|de
operator|.
name|codecentric
operator|.
name|centerdevice
operator|.
name|javafxsvg
operator|.
name|SvgImageLoaderFactory
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
DECL|class|AboutDialogViewModel
specifier|public
class|class
name|AboutDialogViewModel
block|{
DECL|field|HOMEPAGE
specifier|private
specifier|final
name|String
name|HOMEPAGE
init|=
literal|"http://www.jabref.org"
decl_stmt|;
DECL|field|DONATION
specifier|private
specifier|final
name|String
name|DONATION
init|=
literal|"http://www.jabref.org/#donations"
decl_stmt|;
DECL|field|LIBRARIES
specifier|private
specifier|final
name|String
name|LIBRARIES
init|=
literal|"https://github.com/JabRef/jabref/blob/master/external-libraries.txt"
decl_stmt|;
DECL|field|GITHUB
specifier|private
specifier|final
name|String
name|GITHUB
init|=
literal|"https://github.com/JabRef/jabref"
decl_stmt|;
DECL|field|CHANGELOG
specifier|private
specifier|final
name|String
name|CHANGELOG
init|=
literal|"https://github.com/JabRef/jabref/blob/master/CHANGELOG.md"
decl_stmt|;
DECL|field|LICENSE
specifier|private
specifier|final
name|String
name|LICENSE
init|=
literal|"https://github.com/JabRef/jabref/blob/master/LICENSE.md"
decl_stmt|;
DECL|field|logger
specifier|private
specifier|final
name|Log
name|logger
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|AboutDialogViewModel
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|license
specifier|private
specifier|final
name|ReadOnlyStringWrapper
name|license
init|=
operator|new
name|ReadOnlyStringWrapper
argument_list|()
decl_stmt|;
DECL|field|heading
specifier|private
specifier|final
name|ReadOnlyStringWrapper
name|heading
init|=
operator|new
name|ReadOnlyStringWrapper
argument_list|()
decl_stmt|;
DECL|field|authors
specifier|private
specifier|final
name|ReadOnlyStringWrapper
name|authors
init|=
operator|new
name|ReadOnlyStringWrapper
argument_list|()
decl_stmt|;
DECL|field|developers
specifier|private
specifier|final
name|ReadOnlyStringWrapper
name|developers
init|=
operator|new
name|ReadOnlyStringWrapper
argument_list|()
decl_stmt|;
annotation|@
name|FXML
DECL|field|closeButton
specifier|private
name|Button
name|closeButton
decl_stmt|;
annotation|@
name|FXML
DECL|field|iconImage
specifier|private
name|ImageView
name|iconImage
decl_stmt|;
annotation|@
name|FXML
DECL|method|initialize ()
specifier|private
name|void
name|initialize
parameter_list|()
block|{
name|heading
operator|.
name|set
argument_list|(
literal|"JabRef "
operator|+
name|Globals
operator|.
name|BUILD_INFO
operator|.
name|getVersion
argument_list|()
argument_list|)
expr_stmt|;
name|developers
operator|.
name|set
argument_list|(
name|Globals
operator|.
name|BUILD_INFO
operator|.
name|getDevelopers
argument_list|()
argument_list|)
expr_stmt|;
name|authors
operator|.
name|set
argument_list|(
name|Globals
operator|.
name|BUILD_INFO
operator|.
name|getAuthors
argument_list|()
argument_list|)
expr_stmt|;
name|String
name|licenseText
init|=
name|String
operator|.
name|format
argument_list|(
literal|"MIT License (2003 - %s)"
argument_list|,
name|Globals
operator|.
name|BUILD_INFO
operator|.
name|getYear
argument_list|()
argument_list|)
decl_stmt|;
name|license
operator|.
name|set
argument_list|(
name|licenseText
argument_list|)
expr_stmt|;
name|SvgImageLoaderFactory
operator|.
name|install
argument_list|()
expr_stmt|;
name|Image
name|icon
init|=
operator|new
name|Image
argument_list|(
name|this
operator|.
name|getClass
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
literal|"/images/icons/JabRef-icon.svg"
argument_list|)
argument_list|)
decl_stmt|;
name|iconImage
operator|.
name|setImage
argument_list|(
name|icon
argument_list|)
expr_stmt|;
block|}
DECL|method|licenseProperty ()
specifier|public
name|ReadOnlyStringProperty
name|licenseProperty
parameter_list|()
block|{
return|return
name|license
operator|.
name|getReadOnlyProperty
argument_list|()
return|;
block|}
DECL|method|getLicense ()
specifier|public
name|String
name|getLicense
parameter_list|()
block|{
return|return
name|license
operator|.
name|get
argument_list|()
return|;
block|}
DECL|method|authorsProperty ()
specifier|public
name|ReadOnlyStringProperty
name|authorsProperty
parameter_list|()
block|{
return|return
name|authors
operator|.
name|getReadOnlyProperty
argument_list|()
return|;
block|}
DECL|method|getAuthors ()
specifier|public
name|String
name|getAuthors
parameter_list|()
block|{
return|return
name|authors
operator|.
name|get
argument_list|()
return|;
block|}
DECL|method|developersProperty ()
specifier|public
name|ReadOnlyStringProperty
name|developersProperty
parameter_list|()
block|{
return|return
name|developers
operator|.
name|getReadOnlyProperty
argument_list|()
return|;
block|}
DECL|method|getDevelopers ()
specifier|public
name|String
name|getDevelopers
parameter_list|()
block|{
return|return
name|developers
operator|.
name|get
argument_list|()
return|;
block|}
DECL|method|headingProperty ()
specifier|public
name|ReadOnlyStringProperty
name|headingProperty
parameter_list|()
block|{
return|return
name|heading
operator|.
name|getReadOnlyProperty
argument_list|()
return|;
block|}
DECL|method|getHeading ()
specifier|public
name|String
name|getHeading
parameter_list|()
block|{
return|return
name|heading
operator|.
name|get
argument_list|()
return|;
block|}
annotation|@
name|FXML
DECL|method|closeAboutDialog ()
specifier|private
name|void
name|closeAboutDialog
parameter_list|()
block|{
name|Stage
name|stage
init|=
operator|(
name|Stage
operator|)
name|closeButton
operator|.
name|getScene
argument_list|()
operator|.
name|getWindow
argument_list|()
decl_stmt|;
name|stage
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
annotation|@
name|FXML
DECL|method|copyVersionToClipboard ()
specifier|private
name|void
name|copyVersionToClipboard
parameter_list|()
block|{
name|String
name|info
init|=
name|String
operator|.
name|format
argument_list|(
literal|"JabRef %s%n%s %s %s %nJava %s"
argument_list|,
name|Globals
operator|.
name|BUILD_INFO
operator|.
name|getVersion
argument_list|()
argument_list|,
name|BuildInfo
operator|.
name|OS
argument_list|,
name|BuildInfo
operator|.
name|OS_VERSION
argument_list|,
name|BuildInfo
operator|.
name|OS_ARCH
argument_list|,
name|BuildInfo
operator|.
name|JAVA_VERSION
argument_list|)
decl_stmt|;
operator|new
name|ClipBoardManager
argument_list|()
operator|.
name|setClipboardContents
argument_list|(
name|info
argument_list|)
expr_stmt|;
name|JabRefGUI
operator|.
name|getMainFrame
argument_list|()
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Copied version to clipboard"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|FXML
DECL|method|openJabrefWebsite ()
specifier|private
name|void
name|openJabrefWebsite
parameter_list|()
block|{
name|openWebsite
argument_list|(
name|HOMEPAGE
argument_list|)
expr_stmt|;
block|}
annotation|@
name|FXML
DECL|method|openExternalLibrariesWebsite ()
specifier|private
name|void
name|openExternalLibrariesWebsite
parameter_list|()
block|{
name|openWebsite
argument_list|(
name|LIBRARIES
argument_list|)
expr_stmt|;
block|}
annotation|@
name|FXML
DECL|method|openGithub ()
specifier|private
name|void
name|openGithub
parameter_list|()
block|{
name|openWebsite
argument_list|(
name|GITHUB
argument_list|)
expr_stmt|;
block|}
annotation|@
name|FXML
DECL|method|openChangeLog ()
specifier|private
name|void
name|openChangeLog
parameter_list|()
block|{
name|openWebsite
argument_list|(
name|CHANGELOG
argument_list|)
expr_stmt|;
block|}
annotation|@
name|FXML
DECL|method|openLicense ()
specifier|public
name|void
name|openLicense
parameter_list|()
block|{
name|openWebsite
argument_list|(
name|LICENSE
argument_list|)
expr_stmt|;
block|}
annotation|@
name|FXML
DECL|method|openDonation ()
specifier|public
name|void
name|openDonation
parameter_list|()
block|{
name|openWebsite
argument_list|(
name|DONATION
argument_list|)
expr_stmt|;
block|}
DECL|method|openWebsite (String url)
specifier|private
name|void
name|openWebsite
parameter_list|(
name|String
name|url
parameter_list|)
block|{
try|try
block|{
name|JabRefDesktop
operator|.
name|openBrowser
argument_list|(
name|url
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|JabRefGUI
operator|.
name|getMainFrame
argument_list|()
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error"
argument_list|)
operator|+
literal|": "
operator|+
name|e
operator|.
name|getLocalizedMessage
argument_list|()
argument_list|)
expr_stmt|;
name|logger
operator|.
name|debug
argument_list|(
literal|"Could not open default browser."
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

