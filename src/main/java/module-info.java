begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_decl_stmt
name|open
name|module
name|org
operator|.
name|jabref
block|{
name|exports
name|org
operator|.
name|jabref
decl_stmt|;
name|exports
name|org
operator|.
name|jabref
operator|.
name|gui
decl_stmt|;
name|exports
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|logging
decl_stmt|;
name|exports
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|maintable
decl_stmt|;
name|exports
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|specialfields
decl_stmt|;
name|exports
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|database
decl_stmt|;
name|exports
name|org
operator|.
name|jabref
operator|.
name|logic
decl_stmt|;
name|exports
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|citationstyle
decl_stmt|;
name|exports
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|search
decl_stmt|;
comment|// Swing
name|requires
name|java
operator|.
name|desktop
decl_stmt|;
comment|// SQL
name|requires
name|java
operator|.
name|sql
decl_stmt|;
comment|// JavaFX
name|requires
name|javafx
operator|.
name|graphics
decl_stmt|;
name|requires
name|javafx
operator|.
name|swing
decl_stmt|;
name|requires
name|javafx
operator|.
name|controls
decl_stmt|;
name|requires
name|javafx
operator|.
name|web
decl_stmt|;
name|requires
name|javafx
operator|.
name|fxml
decl_stmt|;
name|requires
name|afterburner
operator|.
name|fx
decl_stmt|;
name|requires
name|com
operator|.
name|jfoenix
decl_stmt|;
name|requires
name|de
operator|.
name|saxsys
operator|.
name|mvvmfx
decl_stmt|;
name|requires
name|de
operator|.
name|jensd
operator|.
name|fx
operator|.
name|fontawesomefx
operator|.
name|commons
decl_stmt|;
name|requires
name|de
operator|.
name|jensd
operator|.
name|fx
operator|.
name|fontawesomefx
operator|.
name|materialdesignicons
decl_stmt|;
name|requires
name|org
operator|.
name|controlsfx
operator|.
name|controls
decl_stmt|;
name|provides
name|com
operator|.
name|airhacks
operator|.
name|afterburner
operator|.
name|views
operator|.
name|ResourceLocator
name|with
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|util
operator|.
name|JabRefResourceLocator
decl_stmt|;
name|provides
name|com
operator|.
name|airhacks
operator|.
name|afterburner
operator|.
name|injection
operator|.
name|PresenterFactory
name|with
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|DefaultInjector
decl_stmt|;
comment|// Logging
name|requires
name|org
operator|.
name|slf4j
decl_stmt|;
name|requires
name|org
operator|.
name|apache
operator|.
name|logging
operator|.
name|log4j
decl_stmt|;
name|requires
name|org
operator|.
name|apache
operator|.
name|logging
operator|.
name|log4j
operator|.
name|core
decl_stmt|;
name|requires
name|applicationinsights
operator|.
name|logging
operator|.
name|log4j2
decl_stmt|;
comment|// Preferences and XML
name|requires
name|java
operator|.
name|prefs
decl_stmt|;
name|requires
name|java
operator|.
name|xml
operator|.
name|bind
decl_stmt|;
name|requires
name|jdk
operator|.
name|xml
operator|.
name|dom
decl_stmt|;
comment|// Annotations (@PostConstruct)
name|requires
name|java
operator|.
name|annotation
decl_stmt|;
comment|// Microsoft application insights
name|requires
name|applicationinsights
operator|.
name|core
decl_stmt|;
comment|// Libre Office
name|requires
name|org
operator|.
name|jabref
operator|.
name|thirdparty
operator|.
name|libreoffice
decl_stmt|;
comment|// Other modules
name|requires
name|commons
operator|.
name|logging
decl_stmt|;
name|requires
name|com
operator|.
name|google
operator|.
name|common
decl_stmt|;
name|requires
name|easybind
decl_stmt|;
name|requires
name|javax
operator|.
name|inject
decl_stmt|;
name|requires
name|pdfbox
decl_stmt|;
name|requires
name|reactfx
decl_stmt|;
name|requires
name|commons
operator|.
name|cli
decl_stmt|;
name|requires
name|httpclient
decl_stmt|;
block|}
end_decl_stmt

end_unit

