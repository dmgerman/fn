begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|// TODO: temporarily removed, LibreOffice, Java9
end_comment

begin_comment
comment|//package org.jabref.gui.openoffice;
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//import java.io.File;
end_comment

begin_comment
comment|//import java.io.IOException;
end_comment

begin_comment
comment|//import java.lang.reflect.InvocationTargetException;
end_comment

begin_comment
comment|//import java.lang.reflect.Method;
end_comment

begin_comment
comment|//import java.net.MalformedURLException;
end_comment

begin_comment
comment|//import java.net.URL;
end_comment

begin_comment
comment|//import java.net.URLClassLoader;
end_comment

begin_comment
comment|//import java.util.ArrayList;
end_comment

begin_comment
comment|//import java.util.Arrays;
end_comment

begin_comment
comment|//import java.util.Collections;
end_comment

begin_comment
comment|//import java.util.Comparator;
end_comment

begin_comment
comment|//import java.util.HashMap;
end_comment

begin_comment
comment|//import java.util.HashSet;
end_comment

begin_comment
comment|//import java.util.LinkedHashMap;
end_comment

begin_comment
comment|//import java.util.List;
end_comment

begin_comment
comment|//import java.util.Map;
end_comment

begin_comment
comment|//import java.util.Objects;
end_comment

begin_comment
comment|//import java.util.Optional;
end_comment

begin_comment
comment|//import java.util.Set;
end_comment

begin_comment
comment|//import java.util.SortedMap;
end_comment

begin_comment
comment|//import java.util.TreeMap;
end_comment

begin_comment
comment|//import java.util.TreeSet;
end_comment

begin_comment
comment|//import java.util.regex.Matcher;
end_comment

begin_comment
comment|//import java.util.regex.Pattern;
end_comment

begin_comment
comment|//import java.util.stream.Collectors;
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//import javax.swing.JList;
end_comment

begin_comment
comment|//import javax.swing.JOptionPane;
end_comment

begin_comment
comment|//import javax.swing.JScrollPane;
end_comment

begin_comment
comment|//import javax.swing.ListSelectionModel;
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//import org.jabref.logic.bibtex.comparator.FieldComparator;
end_comment

begin_comment
comment|//import org.jabref.logic.bibtex.comparator.FieldComparatorStack;
end_comment

begin_comment
comment|//import org.jabref.logic.l10n.Localization;
end_comment

begin_comment
comment|//import org.jabref.logic.layout.Layout;
end_comment

begin_comment
comment|//import org.jabref.logic.openoffice.OOBibStyle;
end_comment

begin_comment
comment|//import org.jabref.logic.openoffice.OOPreFormatter;
end_comment

begin_comment
comment|//import org.jabref.logic.openoffice.OOUtil;
end_comment

begin_comment
comment|//import org.jabref.logic.openoffice.UndefinedBibtexEntry;
end_comment

begin_comment
comment|//import org.jabref.logic.openoffice.UndefinedParagraphFormatException;
end_comment

begin_comment
comment|//import org.jabref.model.database.BibDatabase;
end_comment

begin_comment
comment|//import org.jabref.model.entry.BibEntry;
end_comment

begin_comment
comment|//import org.jabref.model.entry.FieldName;
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//import com.sun.star.awt.Point;
end_comment

begin_comment
comment|//import com.sun.star.beans.IllegalTypeException;
end_comment

begin_comment
comment|//import com.sun.star.beans.NotRemoveableException;
end_comment

begin_comment
comment|//import com.sun.star.beans.PropertyExistException;
end_comment

begin_comment
comment|//import com.sun.star.beans.PropertyVetoException;
end_comment

begin_comment
comment|//import com.sun.star.beans.UnknownPropertyException;
end_comment

begin_comment
comment|//import com.sun.star.beans.XPropertyContainer;
end_comment

begin_comment
comment|//import com.sun.star.beans.XPropertySet;
end_comment

begin_comment
comment|//import com.sun.star.comp.helper.Bootstrap;
end_comment

begin_comment
comment|//import com.sun.star.comp.helper.BootstrapException;
end_comment

begin_comment
comment|//import com.sun.star.container.NoSuchElementException;
end_comment

begin_comment
comment|//import com.sun.star.container.XEnumeration;
end_comment

begin_comment
comment|//import com.sun.star.container.XEnumerationAccess;
end_comment

begin_comment
comment|//import com.sun.star.container.XNameAccess;
end_comment

begin_comment
comment|//import com.sun.star.container.XNamed;
end_comment

begin_comment
comment|//import com.sun.star.document.XDocumentPropertiesSupplier;
end_comment

begin_comment
comment|//import com.sun.star.frame.XComponentLoader;
end_comment

begin_comment
comment|//import com.sun.star.frame.XController;
end_comment

begin_comment
comment|//import com.sun.star.frame.XDesktop;
end_comment

begin_comment
comment|//import com.sun.star.frame.XModel;
end_comment

begin_comment
comment|//import com.sun.star.lang.DisposedException;
end_comment

begin_comment
comment|//import com.sun.star.lang.IllegalArgumentException;
end_comment

begin_comment
comment|//import com.sun.star.lang.Locale;
end_comment

begin_comment
comment|//import com.sun.star.lang.WrappedTargetException;
end_comment

begin_comment
comment|//import com.sun.star.lang.XComponent;
end_comment

begin_comment
comment|//import com.sun.star.lang.XMultiComponentFactory;
end_comment

begin_comment
comment|//import com.sun.star.lang.XMultiServiceFactory;
end_comment

begin_comment
comment|//import com.sun.star.text.XBookmarksSupplier;
end_comment

begin_comment
comment|//import com.sun.star.text.XDocumentIndexesSupplier;
end_comment

begin_comment
comment|//import com.sun.star.text.XFootnote;
end_comment

begin_comment
comment|//import com.sun.star.text.XReferenceMarksSupplier;
end_comment

begin_comment
comment|//import com.sun.star.text.XText;
end_comment

begin_comment
comment|//import com.sun.star.text.XTextContent;
end_comment

begin_comment
comment|//import com.sun.star.text.XTextCursor;
end_comment

begin_comment
comment|//import com.sun.star.text.XTextDocument;
end_comment

begin_comment
comment|//import com.sun.star.text.XTextRange;
end_comment

begin_comment
comment|//import com.sun.star.text.XTextRangeCompare;
end_comment

begin_comment
comment|//import com.sun.star.text.XTextSection;
end_comment

begin_comment
comment|//import com.sun.star.text.XTextSectionsSupplier;
end_comment

begin_comment
comment|//import com.sun.star.text.XTextViewCursor;
end_comment

begin_comment
comment|//import com.sun.star.text.XTextViewCursorSupplier;
end_comment

begin_comment
comment|//import com.sun.star.uno.Any;
end_comment

begin_comment
comment|//import com.sun.star.uno.Type;
end_comment

begin_comment
comment|//import com.sun.star.uno.UnoRuntime;
end_comment

begin_comment
comment|//import com.sun.star.uno.XComponentContext;
end_comment

begin_comment
comment|//import org.slf4j.Logger;
end_comment

begin_comment
comment|//import org.slf4j.LoggerFactory;
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|///**
end_comment

begin_comment
comment|// * Class for manipulating the Bibliography of the currently start document in OpenOffice.
end_comment

begin_comment
comment|// */
end_comment

begin_comment
comment|//class OOBibBase {
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private static final OOPreFormatter POSTFORMATTER = new OOPreFormatter();
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private static final String BIB_SECTION_NAME = "JR_bib";
end_comment

begin_comment
comment|//    private static final String BIB_SECTION_END_NAME = "JR_bib_end";
end_comment

begin_comment
comment|//    private static final String BIB_CITATION = "JR_cite";
end_comment

begin_comment
comment|//    private static final Pattern CITE_PATTERN = Pattern.compile(OOBibBase.BIB_CITATION + "\\d*_(\\d*)_(.*)");
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private static final String CHAR_STYLE_NAME = "CharStyleName";
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private static final int AUTHORYEAR_PAR = 1;
end_comment

begin_comment
comment|//    private static final int AUTHORYEAR_INTEXT = 2;
end_comment

begin_comment
comment|//    private static final int INVISIBLE_CIT = 3;
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private static final Logger LOGGER = LoggerFactory.getLogger(OOBibBase.class);
end_comment

begin_comment
comment|//    private XMultiServiceFactory mxDocFactory;
end_comment

begin_comment
comment|//    private XTextDocument mxDoc;
end_comment

begin_comment
comment|//    private XText text;
end_comment

begin_comment
comment|//    private final XDesktop xDesktop;
end_comment

begin_comment
comment|//    private XTextViewCursorSupplier xViewCursorSupplier;
end_comment

begin_comment
comment|//    private XComponent xCurrentComponent;
end_comment

begin_comment
comment|//    private XPropertySet propertySet;
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private XPropertyContainer userProperties;
end_comment

begin_comment
comment|//    private final boolean atEnd;
end_comment

begin_comment
comment|//    private final Comparator<BibEntry> entryComparator;
end_comment

begin_comment
comment|//    private final Comparator<BibEntry> yearAuthorTitleComparator;
end_comment

begin_comment
comment|//    private final FieldComparator authComp = new FieldComparator(FieldName.AUTHOR);
end_comment

begin_comment
comment|//    private final FieldComparator yearComp = new FieldComparator(FieldName.YEAR);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private final FieldComparator titleComp = new FieldComparator(FieldName.TITLE);
end_comment

begin_comment
comment|//    private final List<Comparator<BibEntry>> authorYearTitleList = new ArrayList<>(3);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private final List<Comparator<BibEntry>> yearAuthorTitleList = new ArrayList<>(3);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private final Map<String, String> uniquefiers = new HashMap<>();
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private List<String> sortedReferenceMarks;
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    public OOBibBase(String pathToOO, boolean atEnd) throws IOException, IllegalAccessException,
end_comment

begin_comment
comment|//            InvocationTargetException, BootstrapException, CreationException, UnknownPropertyException,
end_comment

begin_comment
comment|//            WrappedTargetException, IndexOutOfBoundsException, NoSuchElementException, NoDocumentException {
end_comment

begin_comment
comment|//        authorYearTitleList.add(authComp);
end_comment

begin_comment
comment|//        authorYearTitleList.add(yearComp);
end_comment

begin_comment
comment|//        authorYearTitleList.add(titleComp);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        yearAuthorTitleList.add(yearComp);
end_comment

begin_comment
comment|//        yearAuthorTitleList.add(authComp);
end_comment

begin_comment
comment|//        yearAuthorTitleList.add(titleComp);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        entryComparator = new FieldComparatorStack<>(authorYearTitleList);
end_comment

begin_comment
comment|//        yearAuthorTitleComparator = new FieldComparatorStack<>(yearAuthorTitleList);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        this.atEnd = atEnd;
end_comment

begin_comment
comment|//        xDesktop = simpleBootstrap(pathToOO);
end_comment

begin_comment
comment|//        selectDocument();
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    public boolean isConnectedToDocument() {
end_comment

begin_comment
comment|//        return xCurrentComponent != null;
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    public Optional<String> getCurrentDocumentTitle() {
end_comment

begin_comment
comment|//        if (mxDoc == null) {
end_comment

begin_comment
comment|//            return Optional.empty();
end_comment

begin_comment
comment|//        } else {
end_comment

begin_comment
comment|//            try {
end_comment

begin_comment
comment|//                return Optional
end_comment

begin_comment
comment|//                        .of(String.valueOf(OOUtil.getProperty(mxDoc.getCurrentController().getFrame(), "Title")));
end_comment

begin_comment
comment|//            } catch (UnknownPropertyException | WrappedTargetException e) {
end_comment

begin_comment
comment|//                LOGGER.warn("Could not get document title", e);
end_comment

begin_comment
comment|//                return Optional.empty();
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    public void selectDocument() throws UnknownPropertyException, WrappedTargetException, IndexOutOfBoundsException,
end_comment

begin_comment
comment|//            NoSuchElementException, NoDocumentException {
end_comment

begin_comment
comment|//        List<XTextDocument> textDocumentList = getTextDocuments();
end_comment

begin_comment
comment|//        XTextDocument selected;
end_comment

begin_comment
comment|//        if (textDocumentList.isEmpty()) {
end_comment

begin_comment
comment|//            // No text documents found.
end_comment

begin_comment
comment|//            throw new NoDocumentException("No Writer documents found");
end_comment

begin_comment
comment|//        } else if (textDocumentList.size() == 1) {
end_comment

begin_comment
comment|//            // Get the only one
end_comment

begin_comment
comment|//            selected = textDocumentList.get(0);
end_comment

begin_comment
comment|//        } else {
end_comment

begin_comment
comment|//            // Bring up a dialog
end_comment

begin_comment
comment|//            selected = selectComponent(textDocumentList);
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        if (selected == null) {
end_comment

begin_comment
comment|//            return;
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//        xCurrentComponent = UnoRuntime.queryInterface(XComponent.class, selected);
end_comment

begin_comment
comment|//        mxDoc = selected;
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        UnoRuntime.queryInterface(XDocumentIndexesSupplier.class, xCurrentComponent);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        XModel xModel = UnoRuntime.queryInterface(XModel.class, xCurrentComponent);
end_comment

begin_comment
comment|//        XController xController = xModel.getCurrentController();
end_comment

begin_comment
comment|//        xViewCursorSupplier = UnoRuntime.queryInterface(XTextViewCursorSupplier.class, xController);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        // get a reference to the body text of the document
end_comment

begin_comment
comment|//        text = mxDoc.getText();
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        // Access the text document's multi service factory:
end_comment

begin_comment
comment|//        mxDocFactory = UnoRuntime.queryInterface(XMultiServiceFactory.class, mxDoc);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        XDocumentPropertiesSupplier supp = UnoRuntime.queryInterface(XDocumentPropertiesSupplier.class, mxDoc);
end_comment

begin_comment
comment|//        userProperties = supp.getDocumentProperties().getUserDefinedProperties();
end_comment

begin_comment
comment|//        propertySet = UnoRuntime.queryInterface(XPropertySet.class, userProperties);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private XDesktop simpleBootstrap(String pathToExecutable)
end_comment

begin_comment
comment|//            throws IllegalAccessException, InvocationTargetException, BootstrapException,
end_comment

begin_comment
comment|//            CreationException, IOException {
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        ClassLoader loader = ClassLoader.getSystemClassLoader();
end_comment

begin_comment
comment|//        if (loader instanceof URLClassLoader) {
end_comment

begin_comment
comment|//            URLClassLoader cl = (URLClassLoader) loader;
end_comment

begin_comment
comment|//            Class<URLClassLoader> sysclass = URLClassLoader.class;
end_comment

begin_comment
comment|//            try {
end_comment

begin_comment
comment|//                Method method = sysclass.getDeclaredMethod("addURL", URL.class);
end_comment

begin_comment
comment|//                method.setAccessible(true);
end_comment

begin_comment
comment|//                method.invoke(cl, new File(pathToExecutable).toURI().toURL());
end_comment

begin_comment
comment|//            } catch (SecurityException | NoSuchMethodException | MalformedURLException t) {
end_comment

begin_comment
comment|//                LOGGER.error("Error, could not add URL to system classloader", t);
end_comment

begin_comment
comment|//                cl.close();
end_comment

begin_comment
comment|//                throw new IOException("Error, could not add URL to system classloader", t);
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//        } else {
end_comment

begin_comment
comment|//            LOGGER.error("Error occured, URLClassLoader expected but " + loader.getClass()
end_comment

begin_comment
comment|//                    + " received. Could not continue.");
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        //Get the office component context:
end_comment

begin_comment
comment|//        XComponentContext xContext = Bootstrap.bootstrap();
end_comment

begin_comment
comment|//        //Get the office service manager:
end_comment

begin_comment
comment|//        XMultiComponentFactory xServiceManager = xContext.getServiceManager();
end_comment

begin_comment
comment|//        //Create the desktop, which is the root frame of the
end_comment

begin_comment
comment|//        //hierarchy of frames that contain viewable components:
end_comment

begin_comment
comment|//        Object desktop;
end_comment

begin_comment
comment|//        try {
end_comment

begin_comment
comment|//            desktop = xServiceManager.createInstanceWithContext("com.sun.star.frame.Desktop", xContext);
end_comment

begin_comment
comment|//        } catch (Exception e) {
end_comment

begin_comment
comment|//            throw new CreationException(e.getMessage());
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//        XDesktop resultDesktop = UnoRuntime.queryInterface(XDesktop.class, desktop);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        UnoRuntime.queryInterface(XComponentLoader.class, desktop);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        return resultDesktop;
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private List<XTextDocument> getTextDocuments() throws NoSuchElementException, WrappedTargetException {
end_comment

begin_comment
comment|//        List<XTextDocument> result = new ArrayList<>();
end_comment

begin_comment
comment|//        XEnumerationAccess enumAccess = xDesktop.getComponents();
end_comment

begin_comment
comment|//        XEnumeration componentEnumeration = enumAccess.createEnumeration();
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        // TODO: http://api.openoffice.org/docs/DevelopersGuide/OfficeDev/OfficeDev.xhtml#1_1_3_2_1_2_Frame_Hierarchies
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        while (componentEnumeration.hasMoreElements()) {
end_comment

begin_comment
comment|//            Object nextElement = componentEnumeration.nextElement();
end_comment

begin_comment
comment|//            XComponent component = UnoRuntime.queryInterface(XComponent.class, nextElement);
end_comment

begin_comment
comment|//            XTextDocument document = UnoRuntime.queryInterface(XTextDocument.class, component);
end_comment

begin_comment
comment|//            if (document != null) {
end_comment

begin_comment
comment|//                result.add(document);
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//        return result;
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    public void setCustomProperty(String property, String value) throws UnknownPropertyException,
end_comment

begin_comment
comment|//            NotRemoveableException, PropertyExistException, IllegalTypeException, IllegalArgumentException {
end_comment

begin_comment
comment|//        if (propertySet.getPropertySetInfo().hasPropertyByName(property)) {
end_comment

begin_comment
comment|//            userProperties.removeProperty(property);
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//        if (value != null) {
end_comment

begin_comment
comment|//            userProperties.addProperty(property, com.sun.star.beans.PropertyAttribute.REMOVEABLE,
end_comment

begin_comment
comment|//                    new Any(Type.STRING, value));
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    public Optional<String> getCustomProperty(String property) throws UnknownPropertyException, WrappedTargetException {
end_comment

begin_comment
comment|//        if (propertySet.getPropertySetInfo().hasPropertyByName(property)) {
end_comment

begin_comment
comment|//            return Optional.ofNullable(propertySet.getPropertyValue(property).toString());
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//        return Optional.empty();
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    public void updateSortedReferenceMarks() throws WrappedTargetException, NoSuchElementException {
end_comment

begin_comment
comment|//        sortedReferenceMarks = getSortedReferenceMarks(getReferenceMarks());
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    /**
end_comment

begin_comment
comment|//     * This method inserts a cite marker in the text for the given BibEntry,
end_comment

begin_comment
comment|//     * and may refresh the bibliography.
end_comment

begin_comment
comment|//     * @param entries The entries to cite.
end_comment

begin_comment
comment|//     * @param database The database the entry belongs to.
end_comment

begin_comment
comment|//     * @param style The bibliography style we are using.
end_comment

begin_comment
comment|//     * @param inParenthesis Indicates whether it is an in-text citation or a citation in parenthesis.
end_comment

begin_comment
comment|//     *   This is not relevant if numbered citations are used.
end_comment

begin_comment
comment|//     * @param withText Indicates whether this should be a normal citation (true) or an empty
end_comment

begin_comment
comment|//     *   (invisible) citation (false).
end_comment

begin_comment
comment|//     * @param sync Indicates whether the reference list should be refreshed.
end_comment

begin_comment
comment|//     * @throws IllegalTypeException
end_comment

begin_comment
comment|//     * @throws PropertyExistException
end_comment

begin_comment
comment|//     * @throws NotRemoveableException
end_comment

begin_comment
comment|//     * @throws UnknownPropertyException
end_comment

begin_comment
comment|//     * @throws UndefinedCharacterFormatException
end_comment

begin_comment
comment|//     * @throws NoSuchElementException
end_comment

begin_comment
comment|//     * @throws WrappedTargetException
end_comment

begin_comment
comment|//     * @throws IOException
end_comment

begin_comment
comment|//     * @throws PropertyVetoException
end_comment

begin_comment
comment|//     * @throws CreationException
end_comment

begin_comment
comment|//     * @throws BibEntryNotFoundException
end_comment

begin_comment
comment|//     * @throws UndefinedParagraphFormatException
end_comment

begin_comment
comment|//     */
end_comment

begin_comment
comment|//    public void insertEntry(List<BibEntry> entries, BibDatabase database,
end_comment

begin_comment
comment|//            List<BibDatabase> allBases, OOBibStyle style,
end_comment

begin_comment
comment|//            boolean inParenthesis, boolean withText, String pageInfo, boolean sync) throws IllegalArgumentException,
end_comment

begin_comment
comment|//            UnknownPropertyException, NotRemoveableException, PropertyExistException, IllegalTypeException,
end_comment

begin_comment
comment|//            UndefinedCharacterFormatException, WrappedTargetException, NoSuchElementException, PropertyVetoException,
end_comment

begin_comment
comment|//            IOException, CreationException, BibEntryNotFoundException, UndefinedParagraphFormatException {
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        try {
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//            XTextViewCursor xViewCursor = xViewCursorSupplier.getViewCursor();
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//            if (entries.size()> 1) {
end_comment

begin_comment
comment|//                if (style.getBooleanCitProperty(OOBibStyle.MULTI_CITE_CHRONOLOGICAL)) {
end_comment

begin_comment
comment|//                    entries.sort(yearAuthorTitleComparator);
end_comment

begin_comment
comment|//                } else {
end_comment

begin_comment
comment|//                    entries.sort(entryComparator);
end_comment

begin_comment
comment|//                }
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//            String keyString = String.join(",",
end_comment

begin_comment
comment|//                    entries.stream().map(entry -> entry.getCiteKeyOptional().orElse("")).collect(Collectors.toList()));
end_comment

begin_comment
comment|//            // Insert bookmark:
end_comment

begin_comment
comment|//            String bName = getUniqueReferenceMarkName(keyString,
end_comment

begin_comment
comment|//                    withText ? inParenthesis ? OOBibBase.AUTHORYEAR_PAR : OOBibBase.AUTHORYEAR_INTEXT : OOBibBase.INVISIBLE_CIT);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//            // If we should store metadata for page info, do that now:
end_comment

begin_comment
comment|//            if (pageInfo != null) {
end_comment

begin_comment
comment|//                LOGGER.info("Storing page info: " + pageInfo);
end_comment

begin_comment
comment|//                setCustomProperty(bName, pageInfo);
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//            xViewCursor.getText().insertString(xViewCursor, " ", false);
end_comment

begin_comment
comment|//            if (style.isFormatCitations()) {
end_comment

begin_comment
comment|//                XPropertySet xCursorProps = UnoRuntime.queryInterface(XPropertySet.class, xViewCursor);
end_comment

begin_comment
comment|//                String charStyle = style.getCitationCharacterFormat();
end_comment

begin_comment
comment|//                try {
end_comment

begin_comment
comment|//                    xCursorProps.setPropertyValue(CHAR_STYLE_NAME, charStyle);
end_comment

begin_comment
comment|//                } catch (UnknownPropertyException | PropertyVetoException | IllegalArgumentException |
end_comment

begin_comment
comment|//                        WrappedTargetException ex) {
end_comment

begin_comment
comment|//                    // Setting the character format failed, so we throw an exception that
end_comment

begin_comment
comment|//                    // will result in an error message for the user. Before that,
end_comment

begin_comment
comment|//                    // delete the space we inserted:
end_comment

begin_comment
comment|//                    xViewCursor.goLeft((short) 1, true);
end_comment

begin_comment
comment|//                    xViewCursor.setString("");
end_comment

begin_comment
comment|//                    throw new UndefinedCharacterFormatException(charStyle);
end_comment

begin_comment
comment|//                }
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//            xViewCursor.goLeft((short) 1, false);
end_comment

begin_comment
comment|//            Map<BibEntry, BibDatabase> databaseMap = new HashMap<>();
end_comment

begin_comment
comment|//            for (BibEntry entry : entries) {
end_comment

begin_comment
comment|//                databaseMap.put(entry, database);
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//            String citeText = style.isNumberEntries() ? "-" : style.getCitationMarker(entries, databaseMap,
end_comment

begin_comment
comment|//                    inParenthesis, null, null);
end_comment

begin_comment
comment|//            insertReferenceMark(bName, citeText, xViewCursor, withText, style);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//            xViewCursor.collapseToEnd();
end_comment

begin_comment
comment|//            xViewCursor.goRight((short) 1, false);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//            XTextRange position = xViewCursor.getEnd();
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//            if (sync) {
end_comment

begin_comment
comment|//                // To account for numbering and for uniqiefiers, we must refresh the cite markers:
end_comment

begin_comment
comment|//                updateSortedReferenceMarks();
end_comment

begin_comment
comment|//                refreshCiteMarkers(allBases, style);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//                // Insert it at the current position:
end_comment

begin_comment
comment|//                rebuildBibTextSection(allBases, style);
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//            // Go back to the relevant position:
end_comment

begin_comment
comment|//            xViewCursor.gotoRange(position, false);
end_comment

begin_comment
comment|//        } catch (DisposedException ex) {
end_comment

begin_comment
comment|//            // We need to catch this one here because the OpenOfficePanel class is
end_comment

begin_comment
comment|//            // loaded before connection, and therefore cannot directly reference
end_comment

begin_comment
comment|//            // or catch a DisposedException (which is in a OO JAR file).
end_comment

begin_comment
comment|//            throw new ConnectionLostException(ex.getMessage());
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    /**
end_comment

begin_comment
comment|//     * Refresh all cite markers in the document.
end_comment

begin_comment
comment|//     * @param databases The databases to get entries from.
end_comment

begin_comment
comment|//     * @param style The bibliography style to use.
end_comment

begin_comment
comment|//     * @return A list of those referenced BibTeX keys that could not be resolved.
end_comment

begin_comment
comment|//     * @throws UndefinedCharacterFormatException
end_comment

begin_comment
comment|//     * @throws NoSuchElementException
end_comment

begin_comment
comment|//     * @throws IllegalArgumentException
end_comment

begin_comment
comment|//     * @throws WrappedTargetException
end_comment

begin_comment
comment|//     * @throws BibEntryNotFoundException
end_comment

begin_comment
comment|//     * @throws CreationException
end_comment

begin_comment
comment|//     * @throws IOException
end_comment

begin_comment
comment|//     * @throws PropertyVetoException
end_comment

begin_comment
comment|//     * @throws UnknownPropertyException
end_comment

begin_comment
comment|//     */
end_comment

begin_comment
comment|//    public List<String> refreshCiteMarkers(List<BibDatabase> databases, OOBibStyle style)
end_comment

begin_comment
comment|//            throws WrappedTargetException, IllegalArgumentException, NoSuchElementException,
end_comment

begin_comment
comment|//            UndefinedCharacterFormatException, UnknownPropertyException, PropertyVetoException, IOException,
end_comment

begin_comment
comment|//            CreationException, BibEntryNotFoundException {
end_comment

begin_comment
comment|//        try {
end_comment

begin_comment
comment|//            return refreshCiteMarkersInternal(databases, style);
end_comment

begin_comment
comment|//        } catch (DisposedException ex) {
end_comment

begin_comment
comment|//            // We need to catch this one here because the OpenOfficePanel class is
end_comment

begin_comment
comment|//            // loaded before connection, and therefore cannot directly reference
end_comment

begin_comment
comment|//            // or catch a DisposedException (which is in a OO JAR file).
end_comment

begin_comment
comment|//            throw new ConnectionLostException(ex.getMessage());
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    public List<String> getJabRefReferenceMarks(XNameAccess nameAccess) {
end_comment

begin_comment
comment|//        String[] names = nameAccess.getElementNames();
end_comment

begin_comment
comment|//        // Remove all reference marks that don't look like JabRef citations:
end_comment

begin_comment
comment|//        List<String> result = new ArrayList<>();
end_comment

begin_comment
comment|//        if (names != null) {
end_comment

begin_comment
comment|//            for (String name : names) {
end_comment

begin_comment
comment|//                if (CITE_PATTERN.matcher(name).find()) {
end_comment

begin_comment
comment|//                    result.add(name);
end_comment

begin_comment
comment|//                }
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//        return result;
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private List<String> refreshCiteMarkersInternal(List<BibDatabase> databases, OOBibStyle style)
end_comment

begin_comment
comment|//            throws WrappedTargetException, IllegalArgumentException, NoSuchElementException,
end_comment

begin_comment
comment|//            UndefinedCharacterFormatException, UnknownPropertyException, PropertyVetoException,
end_comment

begin_comment
comment|//            CreationException, BibEntryNotFoundException {
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        List<String> cited = findCitedKeys();
end_comment

begin_comment
comment|//        Map<String, BibDatabase> linkSourceBase = new HashMap<>();
end_comment

begin_comment
comment|//        Map<BibEntry, BibDatabase> entries = findCitedEntries(databases, cited, linkSourceBase);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        XNameAccess xReferenceMarks = getReferenceMarks();
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        List<String> names;
end_comment

begin_comment
comment|//        if (style.isSortByPosition()) {
end_comment

begin_comment
comment|//            // We need to sort the reference marks according to their order of appearance:
end_comment

begin_comment
comment|//            names = sortedReferenceMarks;
end_comment

begin_comment
comment|//        } else if (style.isNumberEntries()) {
end_comment

begin_comment
comment|//            // We need to sort the reference marks according to the sorting of the bibliographic
end_comment

begin_comment
comment|//            // entries:
end_comment

begin_comment
comment|//            SortedMap<BibEntry, BibDatabase> newMap = new TreeMap<>(entryComparator);
end_comment

begin_comment
comment|//            for (Map.Entry<BibEntry, BibDatabase> bibtexEntryBibtexDatabaseEntry : entries.entrySet()) {
end_comment

begin_comment
comment|//                newMap.put(bibtexEntryBibtexDatabaseEntry.getKey(), bibtexEntryBibtexDatabaseEntry.getValue());
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//            entries = newMap;
end_comment

begin_comment
comment|//            // Rebuild the list of cited keys according to the sort order:
end_comment

begin_comment
comment|//            cited.clear();
end_comment

begin_comment
comment|//            for (BibEntry entry : entries.keySet()) {
end_comment

begin_comment
comment|//                cited.add(entry.getCiteKeyOptional().orElse(null));
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//            names = Arrays.asList(xReferenceMarks.getElementNames());
end_comment

begin_comment
comment|//        } else {
end_comment

begin_comment
comment|//            names = sortedReferenceMarks;
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        // Remove all reference marks that don't look like JabRef citations:
end_comment

begin_comment
comment|//        List<String> tmp = new ArrayList<>();
end_comment

begin_comment
comment|//        for (String name : names) {
end_comment

begin_comment
comment|//            if (CITE_PATTERN.matcher(name).find()) {
end_comment

begin_comment
comment|//                tmp.add(name);
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//        names = tmp;
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        Map<String, Integer> numbers = new HashMap<>();
end_comment

begin_comment
comment|//        int lastNum = 0;
end_comment

begin_comment
comment|//        // First compute citation markers for all citations:
end_comment

begin_comment
comment|//        String[] citMarkers = new String[names.size()];
end_comment

begin_comment
comment|//        String[][] normCitMarkers = new String[names.size()][];
end_comment

begin_comment
comment|//        String[][] bibtexKeys = new String[names.size()][];
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        int minGroupingCount = style.getIntCitProperty(OOBibStyle.MINIMUM_GROUPING_COUNT);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        int[] types = new int[names.size()];
end_comment

begin_comment
comment|//        for (int i = 0; i< names.size(); i++) {
end_comment

begin_comment
comment|//            Matcher citeMatcher = CITE_PATTERN.matcher(names.get(i));
end_comment

begin_comment
comment|//            if (citeMatcher.find()) {
end_comment

begin_comment
comment|//                String typeStr = citeMatcher.group(1);
end_comment

begin_comment
comment|//                int type = Integer.parseInt(typeStr);
end_comment

begin_comment
comment|//                types[i] = type; // Remember the type in case we need to uniquefy.
end_comment

begin_comment
comment|//                String[] keys = citeMatcher.group(2).split(",");
end_comment

begin_comment
comment|//                bibtexKeys[i] = keys;
end_comment

begin_comment
comment|//                BibEntry[] cEntries = new BibEntry[keys.length];
end_comment

begin_comment
comment|//                for (int j = 0; j< cEntries.length; j++) {
end_comment

begin_comment
comment|//                    BibDatabase database = linkSourceBase.get(keys[j]);
end_comment

begin_comment
comment|//                    Optional<BibEntry> tmpEntry = Optional.empty();
end_comment

begin_comment
comment|//                    if (database != null) {
end_comment

begin_comment
comment|//                        tmpEntry = database.getEntryByKey(keys[j]);
end_comment

begin_comment
comment|//                    }
end_comment

begin_comment
comment|//                    if (tmpEntry.isPresent()) {
end_comment

begin_comment
comment|//                        cEntries[j] = tmpEntry.get();
end_comment

begin_comment
comment|//                    } else {
end_comment

begin_comment
comment|//                        LOGGER.info("BibTeX key not found: '" + keys[j] + '\'');
end_comment

begin_comment
comment|//                        LOGGER.info("Problem with reference mark: '" + names.get(i) + '\'');
end_comment

begin_comment
comment|//                        cEntries[j] = new UndefinedBibtexEntry(keys[j]);
end_comment

begin_comment
comment|//                    }
end_comment

begin_comment
comment|//                }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//                String[] normCitMarker = new String[keys.length];
end_comment

begin_comment
comment|//                String citationMarker;
end_comment

begin_comment
comment|//                if (style.isBibtexKeyCiteMarkers()) {
end_comment

begin_comment
comment|//                    StringBuilder sb = new StringBuilder();
end_comment

begin_comment
comment|//                    normCitMarkers[i] = new String[keys.length];
end_comment

begin_comment
comment|//                    for (int j = 0; j< keys.length; j++) {
end_comment

begin_comment
comment|//                        normCitMarkers[i][j] = cEntries[j].getCiteKeyOptional().orElse(null);
end_comment

begin_comment
comment|//                        sb.append(cEntries[j].getCiteKeyOptional().orElse(""));
end_comment

begin_comment
comment|//                        if (j< (keys.length - 1)) {
end_comment

begin_comment
comment|//                            sb.append(',');
end_comment

begin_comment
comment|//                        }
end_comment

begin_comment
comment|//                    }
end_comment

begin_comment
comment|//                    citationMarker = sb.toString();
end_comment

begin_comment
comment|//                } else if (style.isNumberEntries()) {
end_comment

begin_comment
comment|//                    if (style.isSortByPosition()) {
end_comment

begin_comment
comment|//                        // We have sorted the citation markers according to their order of appearance,
end_comment

begin_comment
comment|//                        // so we simply count up for each marker referring to a new entry:
end_comment

begin_comment
comment|//                        List<Integer> num = new ArrayList<>(keys.length);
end_comment

begin_comment
comment|//                        for (int j = 0; j< keys.length; j++) {
end_comment

begin_comment
comment|//                            if (cEntries[j] instanceof UndefinedBibtexEntry) {
end_comment

begin_comment
comment|//                                num.add(j, -1);
end_comment

begin_comment
comment|//                            } else {
end_comment

begin_comment
comment|//                                num.add(j, lastNum + 1);
end_comment

begin_comment
comment|//                                if (numbers.containsKey(keys[j])) {
end_comment

begin_comment
comment|//                                    num.set(j, numbers.get(keys[j]));
end_comment

begin_comment
comment|//                                } else {
end_comment

begin_comment
comment|//                                    numbers.put(keys[j], num.get(j));
end_comment

begin_comment
comment|//                                    lastNum = num.get(j);
end_comment

begin_comment
comment|//                                }
end_comment

begin_comment
comment|//                            }
end_comment

begin_comment
comment|//                        }
end_comment

begin_comment
comment|//                        citationMarker = style.getNumCitationMarker(num, minGroupingCount, false);
end_comment

begin_comment
comment|//                        for (int j = 0; j< keys.length; j++) {
end_comment

begin_comment
comment|//                            normCitMarker[j] = style.getNumCitationMarker(Collections.singletonList(num.get(j)),
end_comment

begin_comment
comment|//                                    minGroupingCount, false);
end_comment

begin_comment
comment|//                        }
end_comment

begin_comment
comment|//                    } else {
end_comment

begin_comment
comment|//                        // We need to find the number of the cited entry in the bibliography,
end_comment

begin_comment
comment|//                        // and use that number for the cite marker:
end_comment

begin_comment
comment|//                        List<Integer> num = findCitedEntryIndex(names.get(i), cited);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//                        if (num.isEmpty()) {
end_comment

begin_comment
comment|//                            throw new BibEntryNotFoundException(names.get(i), Localization
end_comment

begin_comment
comment|//                                    .lang("Could not resolve BibTeX entry for citation marker '%0'.", names.get(i)));
end_comment

begin_comment
comment|//                        } else {
end_comment

begin_comment
comment|//                            citationMarker = style.getNumCitationMarker(num, minGroupingCount, false);
end_comment

begin_comment
comment|//                        }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//                        for (int j = 0; j< keys.length; j++) {
end_comment

begin_comment
comment|//                            List<Integer> list = new ArrayList<>(1);
end_comment

begin_comment
comment|//                            list.add(num.get(j));
end_comment

begin_comment
comment|//                            normCitMarker[j] = style.getNumCitationMarker(list, minGroupingCount, false);
end_comment

begin_comment
comment|//                        }
end_comment

begin_comment
comment|//                    }
end_comment

begin_comment
comment|//                } else {
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//                    if (cEntries.length> 1) {
end_comment

begin_comment
comment|//                        if (style.getBooleanCitProperty(OOBibStyle.MULTI_CITE_CHRONOLOGICAL)) {
end_comment

begin_comment
comment|//                            Arrays.sort(cEntries, yearAuthorTitleComparator);
end_comment

begin_comment
comment|//                        } else {
end_comment

begin_comment
comment|//                            Arrays.sort(cEntries, entryComparator);
end_comment

begin_comment
comment|//                        }
end_comment

begin_comment
comment|//                        // Update key list to match the new sorting:
end_comment

begin_comment
comment|//                        for (int j = 0; j< cEntries.length; j++) {
end_comment

begin_comment
comment|//                            bibtexKeys[i][j] = cEntries[j].getCiteKeyOptional().orElse(null);
end_comment

begin_comment
comment|//                        }
end_comment

begin_comment
comment|//                    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//                    citationMarker = style.getCitationMarker(Arrays.asList(cEntries), entries,
end_comment

begin_comment
comment|//                            type == OOBibBase.AUTHORYEAR_PAR, null, null);
end_comment

begin_comment
comment|//                    // We need "normalized" (in parenthesis) markers for uniqueness checking purposes:
end_comment

begin_comment
comment|//                    for (int j = 0; j< cEntries.length; j++) {
end_comment

begin_comment
comment|//                        normCitMarker[j] = style.getCitationMarker(Collections.singletonList(cEntries[j]), entries,
end_comment

begin_comment
comment|//                                true, null, new int[] {-1});
end_comment

begin_comment
comment|//                    }
end_comment

begin_comment
comment|//                }
end_comment

begin_comment
comment|//                citMarkers[i] = citationMarker;
end_comment

begin_comment
comment|//                normCitMarkers[i] = normCitMarker;
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        uniquefiers.clear();
end_comment

begin_comment
comment|//        if (!style.isBibtexKeyCiteMarkers()&& !style.isNumberEntries()) {
end_comment

begin_comment
comment|//            // See if there are duplicate citations marks referring to different entries. If so, we need to
end_comment

begin_comment
comment|//            // use uniquefiers:
end_comment

begin_comment
comment|//            Map<String, List<String>> refKeys = new HashMap<>();
end_comment

begin_comment
comment|//            Map<String, List<Integer>> refNums = new HashMap<>();
end_comment

begin_comment
comment|//            for (int i = 0; i< citMarkers.length; i++) {
end_comment

begin_comment
comment|//                String[] markers = normCitMarkers[i]; // compare normalized markers, since the actual markers can be different
end_comment

begin_comment
comment|//                for (int j = 0; j< markers.length; j++) {
end_comment

begin_comment
comment|//                    String marker = markers[j];
end_comment

begin_comment
comment|//                    String currentKey = bibtexKeys[i][j];
end_comment

begin_comment
comment|//                    if (refKeys.containsKey(marker)) {
end_comment

begin_comment
comment|//                        // Ok, we have seen this exact marker before.
end_comment

begin_comment
comment|//                        if (!refKeys.get(marker).contains(currentKey)) {
end_comment

begin_comment
comment|//                            // ... but not for this entry.
end_comment

begin_comment
comment|//                            refKeys.get(marker).add(currentKey);
end_comment

begin_comment
comment|//                            refNums.get(marker).add(i);
end_comment

begin_comment
comment|//                        }
end_comment

begin_comment
comment|//                    } else {
end_comment

begin_comment
comment|//                        List<String> l = new ArrayList<>(1);
end_comment

begin_comment
comment|//                        l.add(currentKey);
end_comment

begin_comment
comment|//                        refKeys.put(marker, l);
end_comment

begin_comment
comment|//                        List<Integer> l2 = new ArrayList<>(1);
end_comment

begin_comment
comment|//                        l2.add(i);
end_comment

begin_comment
comment|//                        refNums.put(marker, l2);
end_comment

begin_comment
comment|//                    }
end_comment

begin_comment
comment|//                }
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//            // Go through the collected lists and see where we need to uniquefy:
end_comment

begin_comment
comment|//            for (Map.Entry<String, List<String>> stringListEntry : refKeys.entrySet()) {
end_comment

begin_comment
comment|//                List<String> keys = stringListEntry.getValue();
end_comment

begin_comment
comment|//                if (keys.size()> 1) {
end_comment

begin_comment
comment|//                    // This marker appears for more than one unique entry:
end_comment

begin_comment
comment|//                    int uniq = 'a';
end_comment

begin_comment
comment|//                    for (String key : keys) {
end_comment

begin_comment
comment|//                        // Update the map of uniquefiers for the benefit of both the following generation of new
end_comment

begin_comment
comment|//                        // citation markers, and for the method that builds the bibliography:
end_comment

begin_comment
comment|//                        uniquefiers.put(key, String.valueOf((char) uniq));
end_comment

begin_comment
comment|//                        uniq++;
end_comment

begin_comment
comment|//                    }
end_comment

begin_comment
comment|//                }
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//            // Finally, go through all citation markers, and update those referring to entries in our current list:
end_comment

begin_comment
comment|//            int maxAuthorsFirst = style.getIntCitProperty(OOBibStyle.MAX_AUTHORS_FIRST);
end_comment

begin_comment
comment|//            Set<String> seenBefore = new HashSet<>();
end_comment

begin_comment
comment|//            for (int j = 0; j< bibtexKeys.length; j++) {
end_comment

begin_comment
comment|//                boolean needsChange = false;
end_comment

begin_comment
comment|//                int[] firstLimAuthors = new int[bibtexKeys[j].length];
end_comment

begin_comment
comment|//                String[] uniquif = new String[bibtexKeys[j].length];
end_comment

begin_comment
comment|//                BibEntry[] cEntries = new BibEntry[bibtexKeys[j].length];
end_comment

begin_comment
comment|//                for (int k = 0; k< bibtexKeys[j].length; k++) {
end_comment

begin_comment
comment|//                    String currentKey = bibtexKeys[j][k];
end_comment

begin_comment
comment|//                    firstLimAuthors[k] = -1;
end_comment

begin_comment
comment|//                    if (maxAuthorsFirst> 0) {
end_comment

begin_comment
comment|//                        if (!seenBefore.contains(currentKey)) {
end_comment

begin_comment
comment|//                            firstLimAuthors[k] = maxAuthorsFirst;
end_comment

begin_comment
comment|//                        }
end_comment

begin_comment
comment|//                        seenBefore.add(currentKey);
end_comment

begin_comment
comment|//                    }
end_comment

begin_comment
comment|//                    String uniq = uniquefiers.get(currentKey);
end_comment

begin_comment
comment|//                    Optional<BibEntry> tmpEntry = Optional.empty();
end_comment

begin_comment
comment|//                    if (uniq == null) {
end_comment

begin_comment
comment|//                        if (firstLimAuthors[k]> 0) {
end_comment

begin_comment
comment|//                            needsChange = true;
end_comment

begin_comment
comment|//                            BibDatabase database = linkSourceBase.get(currentKey);
end_comment

begin_comment
comment|//                            if (database != null) {
end_comment

begin_comment
comment|//                                tmpEntry = database.getEntryByKey(currentKey);
end_comment

begin_comment
comment|//                            }
end_comment

begin_comment
comment|//                        } else {
end_comment

begin_comment
comment|//                            BibDatabase database = linkSourceBase.get(currentKey);
end_comment

begin_comment
comment|//                            if (database != null) {
end_comment

begin_comment
comment|//                                tmpEntry = database.getEntryByKey(currentKey);
end_comment

begin_comment
comment|//                            }
end_comment

begin_comment
comment|//                        }
end_comment

begin_comment
comment|//                        uniquif[k] = "";
end_comment

begin_comment
comment|//                    } else {
end_comment

begin_comment
comment|//                        needsChange = true;
end_comment

begin_comment
comment|//                        BibDatabase database = linkSourceBase.get(currentKey);
end_comment

begin_comment
comment|//                        if (database != null) {
end_comment

begin_comment
comment|//                            tmpEntry = database.getEntryByKey(currentKey);
end_comment

begin_comment
comment|//                        }
end_comment

begin_comment
comment|//                        uniquif[k] = uniq;
end_comment

begin_comment
comment|//                    }
end_comment

begin_comment
comment|//                    if (tmpEntry.isPresent()) {
end_comment

begin_comment
comment|//                        cEntries[k] = tmpEntry.get();
end_comment

begin_comment
comment|//                    }
end_comment

begin_comment
comment|//                }
end_comment

begin_comment
comment|//                if (needsChange) {
end_comment

begin_comment
comment|//                    citMarkers[j] = style.getCitationMarker(Arrays.asList(cEntries), entries,
end_comment

begin_comment
comment|//                            types[j] == OOBibBase.AUTHORYEAR_PAR, uniquif, firstLimAuthors);
end_comment

begin_comment
comment|//                }
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        // Refresh all reference marks with the citation markers we computed:
end_comment

begin_comment
comment|//        boolean hadBibSection = getBookmarkRange(OOBibBase.BIB_SECTION_NAME) != null;
end_comment

begin_comment
comment|//        // Check if we are supposed to set a character format for citations:
end_comment

begin_comment
comment|//        boolean mustTestCharFormat = style.isFormatCitations();
end_comment

begin_comment
comment|//        for (int i = 0; i< names.size(); i++) {
end_comment

begin_comment
comment|//            Object referenceMark = xReferenceMarks.getByName(names.get(i));
end_comment

begin_comment
comment|//            XTextContent bookmark = UnoRuntime.queryInterface(XTextContent.class, referenceMark);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//            XTextCursor cursor = bookmark.getAnchor().getText().createTextCursorByRange(bookmark.getAnchor());
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//            if (mustTestCharFormat) {
end_comment

begin_comment
comment|//                // If we are supposed to set character format for citations, must run a test before we
end_comment

begin_comment
comment|//                // delete old citation markers. Otherwise, if the specified character format doesn't
end_comment

begin_comment
comment|//                // exist, we end up deleting the markers before the process crashes due to a the missing
end_comment

begin_comment
comment|//                // format, with catastrophic consequences for the user.
end_comment

begin_comment
comment|//                mustTestCharFormat = false; // need to do this only once
end_comment

begin_comment
comment|//                XPropertySet xCursorProps = UnoRuntime.queryInterface(XPropertySet.class, cursor);
end_comment

begin_comment
comment|//                String charStyle = style.getCitationCharacterFormat();
end_comment

begin_comment
comment|//                try {
end_comment

begin_comment
comment|//                    xCursorProps.setPropertyValue(CHAR_STYLE_NAME, charStyle);
end_comment

begin_comment
comment|//                } catch (UnknownPropertyException | PropertyVetoException | IllegalArgumentException |
end_comment

begin_comment
comment|//                        WrappedTargetException ex) {
end_comment

begin_comment
comment|//                    throw new UndefinedCharacterFormatException(charStyle);
end_comment

begin_comment
comment|//                }
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//            text.removeTextContent(bookmark);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//            insertReferenceMark(names.get(i), citMarkers[i], cursor, types[i] != OOBibBase.INVISIBLE_CIT, style);
end_comment

begin_comment
comment|//            if (hadBibSection&& (getBookmarkRange(OOBibBase.BIB_SECTION_NAME) == null)) {
end_comment

begin_comment
comment|//                // We have overwritten the marker for the start of the reference list.
end_comment

begin_comment
comment|//                // We need to add it again.
end_comment

begin_comment
comment|//                cursor.collapseToEnd();
end_comment

begin_comment
comment|//                OOUtil.insertParagraphBreak(text, cursor);
end_comment

begin_comment
comment|//                insertBookMark(OOBibBase.BIB_SECTION_NAME, cursor);
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        List<String> unresolvedKeys = new ArrayList<>();
end_comment

begin_comment
comment|//        for (BibEntry entry : entries.keySet()) {
end_comment

begin_comment
comment|//            if (entry instanceof UndefinedBibtexEntry) {
end_comment

begin_comment
comment|//                String key = ((UndefinedBibtexEntry) entry).getKey();
end_comment

begin_comment
comment|//                if (!unresolvedKeys.contains(key)) {
end_comment

begin_comment
comment|//                    unresolvedKeys.add(key);
end_comment

begin_comment
comment|//                }
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//        return unresolvedKeys;
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private List<String> getSortedReferenceMarks(final XNameAccess nameAccess)
end_comment

begin_comment
comment|//            throws WrappedTargetException, NoSuchElementException {
end_comment

begin_comment
comment|//        XTextViewCursorSupplier cursorSupplier = UnoRuntime.queryInterface(XTextViewCursorSupplier.class,
end_comment

begin_comment
comment|//                mxDoc.getCurrentController());
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        XTextViewCursor viewCursor = cursorSupplier.getViewCursor();
end_comment

begin_comment
comment|//        XTextRange initialPos = viewCursor.getStart();
end_comment

begin_comment
comment|//        List<String> names = Arrays.asList(nameAccess.getElementNames());
end_comment

begin_comment
comment|//        List<Point> positions = new ArrayList<>(names.size());
end_comment

begin_comment
comment|//        for (String name : names) {
end_comment

begin_comment
comment|//            XTextContent textContent = UnoRuntime.queryInterface(XTextContent.class, nameAccess.getByName(name));
end_comment

begin_comment
comment|//            XTextRange range = textContent.getAnchor();
end_comment

begin_comment
comment|//            // Check if we are inside a footnote:
end_comment

begin_comment
comment|//            if (UnoRuntime.queryInterface(XFootnote.class, range.getText()) != null) {
end_comment

begin_comment
comment|//                // Find the linking footnote marker:
end_comment

begin_comment
comment|//                XFootnote footer = UnoRuntime.queryInterface(XFootnote.class, range.getText());
end_comment

begin_comment
comment|//                // The footnote's anchor gives the correct position in the text:
end_comment

begin_comment
comment|//                range = footer.getAnchor();
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//            positions.add(findPosition(viewCursor, range));
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//        Set<ComparableMark> set = new TreeSet<>();
end_comment

begin_comment
comment|//        for (int i = 0; i< positions.size(); i++) {
end_comment

begin_comment
comment|//            set.add(new ComparableMark(names.get(i), positions.get(i)));
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        List<String> result = new ArrayList<>(set.size());
end_comment

begin_comment
comment|//        for (ComparableMark mark : set) {
end_comment

begin_comment
comment|//            result.add(mark.getName());
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//        viewCursor.gotoRange(initialPos, false);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        return result;
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    public void rebuildBibTextSection(List<BibDatabase> databases, OOBibStyle style)
end_comment

begin_comment
comment|//            throws NoSuchElementException, WrappedTargetException, IllegalArgumentException,
end_comment

begin_comment
comment|//            CreationException, PropertyVetoException, UnknownPropertyException, UndefinedParagraphFormatException {
end_comment

begin_comment
comment|//        List<String> cited = findCitedKeys();
end_comment

begin_comment
comment|//        Map<String, BibDatabase> linkSourceBase = new HashMap<>();
end_comment

begin_comment
comment|//        Map<BibEntry, BibDatabase> entries = findCitedEntries(databases, cited, linkSourceBase); // Although entries are redefined without use, this also updates linkSourceBase
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        List<String> names = sortedReferenceMarks;
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        if (style.isSortByPosition()) {
end_comment

begin_comment
comment|//            // We need to sort the entries according to their order of appearance:
end_comment

begin_comment
comment|//            entries = getSortedEntriesFromSortedRefMarks(names, linkSourceBase);
end_comment

begin_comment
comment|//        } else {
end_comment

begin_comment
comment|//            SortedMap<BibEntry, BibDatabase> newMap = new TreeMap<>(entryComparator);
end_comment

begin_comment
comment|//            for (Map.Entry<BibEntry, BibDatabase> bibtexEntryBibtexDatabaseEntry : findCitedEntries(databases, cited,
end_comment

begin_comment
comment|//                    linkSourceBase).entrySet()) {
end_comment

begin_comment
comment|//                newMap.put(bibtexEntryBibtexDatabaseEntry.getKey(), bibtexEntryBibtexDatabaseEntry.getValue());
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//            entries = newMap;
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//        clearBibTextSectionContent2();
end_comment

begin_comment
comment|//        populateBibTextSection(entries, style);
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    public XNameAccess getReferenceMarks() {
end_comment

begin_comment
comment|//        XReferenceMarksSupplier supplier = UnoRuntime.queryInterface(XReferenceMarksSupplier.class, xCurrentComponent);
end_comment

begin_comment
comment|//        return supplier.getReferenceMarks();
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private String getUniqueReferenceMarkName(String bibtexKey, int type) {
end_comment

begin_comment
comment|//        XNameAccess xNamedRefMarks = getReferenceMarks();
end_comment

begin_comment
comment|//        int i = 0;
end_comment

begin_comment
comment|//        String name = OOBibBase.BIB_CITATION + '_' + type + '_' + bibtexKey;
end_comment

begin_comment
comment|//        while (xNamedRefMarks.hasByName(name)) {
end_comment

begin_comment
comment|//            name = OOBibBase.BIB_CITATION + i + '_' + type + '_' + bibtexKey;
end_comment

begin_comment
comment|//            i++;
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//        return name;
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private Map<BibEntry, BibDatabase> findCitedEntries(List<BibDatabase> databases, List<String> keys,
end_comment

begin_comment
comment|//            Map<String, BibDatabase> linkSourceBase) {
end_comment

begin_comment
comment|//        Map<BibEntry, BibDatabase> entries = new LinkedHashMap<>();
end_comment

begin_comment
comment|//        for (String key : keys) {
end_comment

begin_comment
comment|//            boolean found = false;
end_comment

begin_comment
comment|//            for (BibDatabase database : databases) {
end_comment

begin_comment
comment|//                Optional<BibEntry> entry = database.getEntryByKey(key);
end_comment

begin_comment
comment|//                if (entry.isPresent()) {
end_comment

begin_comment
comment|//                    entries.put(entry.get(), database);
end_comment

begin_comment
comment|//                    linkSourceBase.put(key, database);
end_comment

begin_comment
comment|//                    found = true;
end_comment

begin_comment
comment|//                    break;
end_comment

begin_comment
comment|//                }
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//            if (!found) {
end_comment

begin_comment
comment|//                entries.put(new UndefinedBibtexEntry(key), null);
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//        return entries;
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private List<String> findCitedKeys() throws NoSuchElementException, WrappedTargetException {
end_comment

begin_comment
comment|//        XNameAccess xNamedMarks = getReferenceMarks();
end_comment

begin_comment
comment|//        String[] names = xNamedMarks.getElementNames();
end_comment

begin_comment
comment|//        List<String> keys = new ArrayList<>();
end_comment

begin_comment
comment|//        for (String name1 : names) {
end_comment

begin_comment
comment|//            Object bookmark = xNamedMarks.getByName(name1);
end_comment

begin_comment
comment|//            UnoRuntime.queryInterface(XTextContent.class, bookmark);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//            List<String> newKeys = parseRefMarkName(name1);
end_comment

begin_comment
comment|//            for (String key : newKeys) {
end_comment

begin_comment
comment|//                if (!keys.contains(key)) {
end_comment

begin_comment
comment|//                    keys.add(key);
end_comment

begin_comment
comment|//                }
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        return keys;
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private Map<BibEntry, BibDatabase> getSortedEntriesFromSortedRefMarks(List<String> names,
end_comment

begin_comment
comment|//            Map<String, BibDatabase> linkSourceBase) {
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        Map<BibEntry, BibDatabase> newList = new LinkedHashMap<>();
end_comment

begin_comment
comment|//        for (String name : names) {
end_comment

begin_comment
comment|//            Matcher citeMatcher = CITE_PATTERN.matcher(name);
end_comment

begin_comment
comment|//            if (citeMatcher.find()) {
end_comment

begin_comment
comment|//                String[] keys = citeMatcher.group(2).split(",");
end_comment

begin_comment
comment|//                for (String key : keys) {
end_comment

begin_comment
comment|//                    BibDatabase database = linkSourceBase.get(key);
end_comment

begin_comment
comment|//                    Optional<BibEntry> origEntry = Optional.empty();
end_comment

begin_comment
comment|//                    if (database != null) {
end_comment

begin_comment
comment|//                        origEntry = database.getEntryByKey(key);
end_comment

begin_comment
comment|//                    }
end_comment

begin_comment
comment|//                    if (origEntry.isPresent()) {
end_comment

begin_comment
comment|//                        if (!newList.containsKey(origEntry.get())) {
end_comment

begin_comment
comment|//                            newList.put(origEntry.get(), database);
end_comment

begin_comment
comment|//                        }
end_comment

begin_comment
comment|//                    } else {
end_comment

begin_comment
comment|//                        LOGGER.info("BibTeX key not found: '" + key + "'");
end_comment

begin_comment
comment|//                        LOGGER.info("Problem with reference mark: '" + name + "'");
end_comment

begin_comment
comment|//                        newList.put(new UndefinedBibtexEntry(key), null);
end_comment

begin_comment
comment|//                    }
end_comment

begin_comment
comment|//                }
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        return newList;
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private Point findPosition(XTextViewCursor cursor, XTextRange range) {
end_comment

begin_comment
comment|//        cursor.gotoRange(range, false);
end_comment

begin_comment
comment|//        return cursor.getPosition();
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    /**
end_comment

begin_comment
comment|//     * Extract the list of bibtex keys from a reference mark name.
end_comment

begin_comment
comment|//     * @param name The reference mark name.
end_comment

begin_comment
comment|//     * @return The list of bibtex keys encoded in the name.
end_comment

begin_comment
comment|//     */
end_comment

begin_comment
comment|//    public List<String> parseRefMarkName(String name) {
end_comment

begin_comment
comment|//        List<String> keys = new ArrayList<>();
end_comment

begin_comment
comment|//        Matcher citeMatcher = CITE_PATTERN.matcher(name);
end_comment

begin_comment
comment|//        if (citeMatcher.find()) {
end_comment

begin_comment
comment|//            String[] keystring = citeMatcher.group(2).split(",");
end_comment

begin_comment
comment|//            for (String aKeystring : keystring) {
end_comment

begin_comment
comment|//                if (!keys.contains(aKeystring)) {
end_comment

begin_comment
comment|//                    keys.add(aKeystring);
end_comment

begin_comment
comment|//                }
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//        return keys;
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    /**
end_comment

begin_comment
comment|//     * Resolve the bibtex key from a citation reference marker name, and look up
end_comment

begin_comment
comment|//     * the index of the key in a list of keys.
end_comment

begin_comment
comment|//     * @param citRefName The name of the ReferenceMark representing the citation.
end_comment

begin_comment
comment|//     * @param keys A List of bibtex keys representing the entries in the bibliography.
end_comment

begin_comment
comment|//     * @return the indices of the cited keys, -1 if a key is not found. Returns null if the ref name
end_comment

begin_comment
comment|//     *   could not be resolved as a citation.
end_comment

begin_comment
comment|//     */
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private List<Integer> findCitedEntryIndex(String citRefName, List<String> keys) {
end_comment

begin_comment
comment|//        Matcher citeMatcher = CITE_PATTERN.matcher(citRefName);
end_comment

begin_comment
comment|//        if (citeMatcher.find()) {
end_comment

begin_comment
comment|//            List<String> keyStrings = Arrays.asList(citeMatcher.group(2).split(","));
end_comment

begin_comment
comment|//            List<Integer> result = new ArrayList<>(keyStrings.size());
end_comment

begin_comment
comment|//            for (String key : keyStrings) {
end_comment

begin_comment
comment|//                int ind = keys.indexOf(key);
end_comment

begin_comment
comment|//                result.add(ind == -1 ? -1 : 1 + ind);
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//            return result;
end_comment

begin_comment
comment|//        } else {
end_comment

begin_comment
comment|//            return Collections.emptyList();
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    public String getCitationContext(XNameAccess nameAccess, String refMarkName, int charBefore, int charAfter,
end_comment

begin_comment
comment|//            boolean htmlMarkup) throws NoSuchElementException, WrappedTargetException {
end_comment

begin_comment
comment|//        Object referenceMark = nameAccess.getByName(refMarkName);
end_comment

begin_comment
comment|//        XTextContent bookmark = UnoRuntime.queryInterface(XTextContent.class, referenceMark);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        XTextCursor cursor = bookmark.getAnchor().getText().createTextCursorByRange(bookmark.getAnchor());
end_comment

begin_comment
comment|//        String citPart = cursor.getString();
end_comment

begin_comment
comment|//        int flex = 8;
end_comment

begin_comment
comment|//        for (int i = 0; i< charBefore; i++) {
end_comment

begin_comment
comment|//            try {
end_comment

begin_comment
comment|//                cursor.goLeft((short) 1, true);
end_comment

begin_comment
comment|//                if ((i>= (charBefore - flex))&& Character.isWhitespace(cursor.getString().charAt(0))) {
end_comment

begin_comment
comment|//                    break;
end_comment

begin_comment
comment|//                }
end_comment

begin_comment
comment|//            } catch (IndexOutOfBoundsException ex) {
end_comment

begin_comment
comment|//                LOGGER.warn("Problem going left", ex);
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//        int length = cursor.getString().length();
end_comment

begin_comment
comment|//        int added = length - citPart.length();
end_comment

begin_comment
comment|//        cursor.collapseToStart();
end_comment

begin_comment
comment|//        for (int i = 0; i< (charAfter + length); i++) {
end_comment

begin_comment
comment|//            try {
end_comment

begin_comment
comment|//                cursor.goRight((short) 1, true);
end_comment

begin_comment
comment|//                if (i>= ((charAfter + length) - flex)) {
end_comment

begin_comment
comment|//                    String strNow = cursor.getString();
end_comment

begin_comment
comment|//                    if (Character.isWhitespace(strNow.charAt(strNow.length() - 1))) {
end_comment

begin_comment
comment|//                        break;
end_comment

begin_comment
comment|//                    }
end_comment

begin_comment
comment|//                }
end_comment

begin_comment
comment|//            } catch (IndexOutOfBoundsException ex) {
end_comment

begin_comment
comment|//                LOGGER.warn("Problem going right", ex);
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        String result = cursor.getString();
end_comment

begin_comment
comment|//        if (htmlMarkup) {
end_comment

begin_comment
comment|//            result = result.substring(0, added) + "<b>" + citPart + "</b>" + result.substring(length);
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//        return result.trim();
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private void insertFullReferenceAtCursor(XTextCursor cursor, Map<BibEntry, BibDatabase> entries, OOBibStyle style,
end_comment

begin_comment
comment|//            String parFormat) throws UndefinedParagraphFormatException, IllegalArgumentException,
end_comment

begin_comment
comment|//                    UnknownPropertyException, PropertyVetoException, WrappedTargetException {
end_comment

begin_comment
comment|//        Map<BibEntry, BibDatabase> correctEntries;
end_comment

begin_comment
comment|//        // If we don't have numbered entries, we need to sort the entries before adding them:
end_comment

begin_comment
comment|//        if (style.isSortByPosition()) {
end_comment

begin_comment
comment|//            // Use the received map directly
end_comment

begin_comment
comment|//            correctEntries = entries;
end_comment

begin_comment
comment|//        } else {
end_comment

begin_comment
comment|//            // Sort map
end_comment

begin_comment
comment|//            Map<BibEntry, BibDatabase> newMap = new TreeMap<>(entryComparator);
end_comment

begin_comment
comment|//            newMap.putAll(entries);
end_comment

begin_comment
comment|//            correctEntries = newMap;
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//        int number = 1;
end_comment

begin_comment
comment|//        for (Map.Entry<BibEntry, BibDatabase> entry : correctEntries.entrySet()) {
end_comment

begin_comment
comment|//            if (entry.getKey() instanceof UndefinedBibtexEntry) {
end_comment

begin_comment
comment|//                continue;
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//            OOUtil.insertParagraphBreak(text, cursor);
end_comment

begin_comment
comment|//            if (style.isNumberEntries()) {
end_comment

begin_comment
comment|//                int minGroupingCount = style.getIntCitProperty(OOBibStyle.MINIMUM_GROUPING_COUNT);
end_comment

begin_comment
comment|//                OOUtil.insertTextAtCurrentLocation(text, cursor,
end_comment

begin_comment
comment|//                        style.getNumCitationMarker(Collections.singletonList(number++), minGroupingCount, true), Collections.emptyList());
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//            Layout layout = style.getReferenceFormat(entry.getKey().getType());
end_comment

begin_comment
comment|//            layout.setPostFormatter(POSTFORMATTER);
end_comment

begin_comment
comment|//            OOUtil.insertFullReferenceAtCurrentLocation(text, cursor, layout, parFormat, entry.getKey(),
end_comment

begin_comment
comment|//                    entry.getValue(), uniquefiers.get(entry.getKey().getCiteKeyOptional().orElse(null)));
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private void createBibTextSection2(boolean end)
end_comment

begin_comment
comment|//            throws IllegalArgumentException, CreationException {
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        XTextCursor mxDocCursor = text.createTextCursor();
end_comment

begin_comment
comment|//        if (end) {
end_comment

begin_comment
comment|//            mxDocCursor.gotoEnd(false);
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//        OOUtil.insertParagraphBreak(text, mxDocCursor);
end_comment

begin_comment
comment|//        // Create a new TextSection from the document factory and access it's XNamed interface
end_comment

begin_comment
comment|//        XNamed xChildNamed;
end_comment

begin_comment
comment|//        try {
end_comment

begin_comment
comment|//            xChildNamed = UnoRuntime.queryInterface(XNamed.class,
end_comment

begin_comment
comment|//                mxDocFactory.createInstance("com.sun.star.text.TextSection"));
end_comment

begin_comment
comment|//        } catch (Exception e) {
end_comment

begin_comment
comment|//            throw new CreationException(e.getMessage());
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//        // Set the new sections name to 'Child_Section'
end_comment

begin_comment
comment|//        xChildNamed.setName(OOBibBase.BIB_SECTION_NAME);
end_comment

begin_comment
comment|//        // Access the Child_Section's XTextContent interface and insert it into the document
end_comment

begin_comment
comment|//        XTextContent xChildSection = UnoRuntime.queryInterface(XTextContent.class, xChildNamed);
end_comment

begin_comment
comment|//        text.insertTextContent(mxDocCursor, xChildSection, false);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private void clearBibTextSectionContent2()
end_comment

begin_comment
comment|//            throws NoSuchElementException, WrappedTargetException, IllegalArgumentException, CreationException {
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        // Check if the section exists:
end_comment

begin_comment
comment|//        XTextSectionsSupplier supplier = UnoRuntime.queryInterface(XTextSectionsSupplier.class, mxDoc);
end_comment

begin_comment
comment|//        if (supplier.getTextSections().hasByName(OOBibBase.BIB_SECTION_NAME)) {
end_comment

begin_comment
comment|//            XTextSection section = (XTextSection) ((Any) supplier.getTextSections().getByName(OOBibBase.BIB_SECTION_NAME))
end_comment

begin_comment
comment|//                    .getObject();
end_comment

begin_comment
comment|//            // Clear it:
end_comment

begin_comment
comment|//            XTextCursor cursor = text.createTextCursorByRange(section.getAnchor());
end_comment

begin_comment
comment|//            cursor.gotoRange(section.getAnchor(), false);
end_comment

begin_comment
comment|//            cursor.setString("");
end_comment

begin_comment
comment|//        } else {
end_comment

begin_comment
comment|//            createBibTextSection2(atEnd);
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private void populateBibTextSection(Map<BibEntry, BibDatabase> entries, OOBibStyle style)
end_comment

begin_comment
comment|//            throws NoSuchElementException, WrappedTargetException, PropertyVetoException,
end_comment

begin_comment
comment|//            UnknownPropertyException, UndefinedParagraphFormatException, IllegalArgumentException, CreationException {
end_comment

begin_comment
comment|//        XTextSectionsSupplier supplier = UnoRuntime.queryInterface(XTextSectionsSupplier.class, mxDoc);
end_comment

begin_comment
comment|//        XTextSection section = (XTextSection) ((Any) supplier.getTextSections().getByName(OOBibBase.BIB_SECTION_NAME))
end_comment

begin_comment
comment|//                .getObject();
end_comment

begin_comment
comment|//        XTextCursor cursor = text.createTextCursorByRange(section.getAnchor());
end_comment

begin_comment
comment|//        OOUtil.insertTextAtCurrentLocation(text, cursor, (String) style.getProperty(OOBibStyle.TITLE),
end_comment

begin_comment
comment|//                (String) style.getProperty(OOBibStyle.REFERENCE_HEADER_PARAGRAPH_FORMAT));
end_comment

begin_comment
comment|//        insertFullReferenceAtCursor(cursor, entries, style,
end_comment

begin_comment
comment|//                (String) style.getProperty(OOBibStyle.REFERENCE_PARAGRAPH_FORMAT));
end_comment

begin_comment
comment|//        insertBookMark(OOBibBase.BIB_SECTION_END_NAME, cursor);
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private XTextContent insertBookMark(String name, XTextCursor position)
end_comment

begin_comment
comment|//            throws IllegalArgumentException, CreationException {
end_comment

begin_comment
comment|//        Object bookmark;
end_comment

begin_comment
comment|//        try {
end_comment

begin_comment
comment|//            bookmark = mxDocFactory.createInstance("com.sun.star.text.Bookmark");
end_comment

begin_comment
comment|//        } catch (Exception e) {
end_comment

begin_comment
comment|//            throw new CreationException(e.getMessage());
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//        // name the bookmark
end_comment

begin_comment
comment|//        XNamed xNamed = UnoRuntime.queryInterface(XNamed.class, bookmark);
end_comment

begin_comment
comment|//        xNamed.setName(name);
end_comment

begin_comment
comment|//        // get XTextContent interface
end_comment

begin_comment
comment|//        XTextContent xTextContent = UnoRuntime.queryInterface(XTextContent.class, bookmark);
end_comment

begin_comment
comment|//        // insert bookmark at the end of the document
end_comment

begin_comment
comment|//        // instead of mxDocText.getEnd you could use a text cursor's XTextRange interface or any XTextRange
end_comment

begin_comment
comment|//        text.insertTextContent(position, xTextContent, true);
end_comment

begin_comment
comment|//        position.collapseToEnd();
end_comment

begin_comment
comment|//        return xTextContent;
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private void insertReferenceMark(String name, String citationText, XTextCursor position, boolean withText,
end_comment

begin_comment
comment|//            OOBibStyle style) throws UnknownPropertyException, WrappedTargetException,
end_comment

begin_comment
comment|//            PropertyVetoException, IllegalArgumentException, UndefinedCharacterFormatException, CreationException {
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        // Check if there is "page info" stored for this citation. If so, insert it into
end_comment

begin_comment
comment|//        // the citation text before inserting the citation:
end_comment

begin_comment
comment|//        Optional<String> pageInfo = getCustomProperty(name);
end_comment

begin_comment
comment|//        String citText;
end_comment

begin_comment
comment|//        if ((pageInfo.isPresent())&& !pageInfo.get().isEmpty()) {
end_comment

begin_comment
comment|//            citText = style.insertPageInfo(citationText, pageInfo.get());
end_comment

begin_comment
comment|//        } else {
end_comment

begin_comment
comment|//            citText = citationText;
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        Object bookmark;
end_comment

begin_comment
comment|//        try {
end_comment

begin_comment
comment|//            bookmark = mxDocFactory.createInstance("com.sun.star.text.ReferenceMark");
end_comment

begin_comment
comment|//        } catch (Exception e) {
end_comment

begin_comment
comment|//            throw new CreationException(e.getMessage());
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//        // Name the reference
end_comment

begin_comment
comment|//        XNamed xNamed = UnoRuntime.queryInterface(XNamed.class, bookmark);
end_comment

begin_comment
comment|//        xNamed.setName(name);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        if (withText) {
end_comment

begin_comment
comment|//            position.setString(citText);
end_comment

begin_comment
comment|//            XPropertySet xCursorProps = UnoRuntime.queryInterface(XPropertySet.class, position);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//            // Set language to [None]:
end_comment

begin_comment
comment|//            xCursorProps.setPropertyValue("CharLocale", new Locale("zxx", "", ""));
end_comment

begin_comment
comment|//            if (style.isFormatCitations()) {
end_comment

begin_comment
comment|//                String charStyle = style.getCitationCharacterFormat();
end_comment

begin_comment
comment|//                try {
end_comment

begin_comment
comment|//                    xCursorProps.setPropertyValue(CHAR_STYLE_NAME, charStyle);
end_comment

begin_comment
comment|//                } catch (UnknownPropertyException | PropertyVetoException | IllegalArgumentException |
end_comment

begin_comment
comment|//                        WrappedTargetException ex) {
end_comment

begin_comment
comment|//                    throw new UndefinedCharacterFormatException(charStyle);
end_comment

begin_comment
comment|//                }
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//        } else {
end_comment

begin_comment
comment|//            position.setString("");
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        // get XTextContent interface
end_comment

begin_comment
comment|//        XTextContent xTextContent = UnoRuntime.queryInterface(XTextContent.class, bookmark);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        position.getText().insertTextContent(position, xTextContent, true);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        // Check if we should italicize the "et al." string in citations:
end_comment

begin_comment
comment|//        boolean italicize = style.getBooleanCitProperty(OOBibStyle.ITALIC_ET_AL);
end_comment

begin_comment
comment|//        if (italicize) {
end_comment

begin_comment
comment|//            String etAlString = style.getStringCitProperty(OOBibStyle.ET_AL_STRING);
end_comment

begin_comment
comment|//            int index = citText.indexOf(etAlString);
end_comment

begin_comment
comment|//            if (index>= 0) {
end_comment

begin_comment
comment|//                italicizeOrBold(position, true, index, index + etAlString.length());
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        position.collapseToEnd();
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private void italicizeOrBold(XTextCursor position, boolean italicize, int start, int end)
end_comment

begin_comment
comment|//            throws UnknownPropertyException, PropertyVetoException, IllegalArgumentException, WrappedTargetException {
end_comment

begin_comment
comment|//        XTextRange range = position.getStart();
end_comment

begin_comment
comment|//        XTextCursor cursor = position.getText().createTextCursorByRange(range);
end_comment

begin_comment
comment|//        cursor.goRight((short) start, false);
end_comment

begin_comment
comment|//        cursor.goRight((short) (end - start), true);
end_comment

begin_comment
comment|//        XPropertySet xcp = UnoRuntime.queryInterface(XPropertySet.class, cursor);
end_comment

begin_comment
comment|//        if (italicize) {
end_comment

begin_comment
comment|//            xcp.setPropertyValue("CharPosture", com.sun.star.awt.FontSlant.ITALIC);
end_comment

begin_comment
comment|//        } else {
end_comment

begin_comment
comment|//            xcp.setPropertyValue("CharWeight", com.sun.star.awt.FontWeight.BOLD);
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private void removeReferenceMark(String name) throws NoSuchElementException, WrappedTargetException {
end_comment

begin_comment
comment|//        XNameAccess xReferenceMarks = getReferenceMarks();
end_comment

begin_comment
comment|//        if (xReferenceMarks.hasByName(name)) {
end_comment

begin_comment
comment|//            Object referenceMark = xReferenceMarks.getByName(name);
end_comment

begin_comment
comment|//            XTextContent bookmark = UnoRuntime.queryInterface(XTextContent.class, referenceMark);
end_comment

begin_comment
comment|//            text.removeTextContent(bookmark);
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    /**
end_comment

begin_comment
comment|//     * Get the XTextRange corresponding to the named bookmark.
end_comment

begin_comment
comment|//     * @param name The name of the bookmark to find.
end_comment

begin_comment
comment|//     * @return The XTextRange for the bookmark.
end_comment

begin_comment
comment|//     * @throws WrappedTargetException
end_comment

begin_comment
comment|//     * @throws NoSuchElementException
end_comment

begin_comment
comment|//     */
end_comment

begin_comment
comment|//    private XTextRange getBookmarkRange(String name) throws NoSuchElementException, WrappedTargetException {
end_comment

begin_comment
comment|//        XNameAccess xNamedBookmarks = getBookmarks();
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        // retrieve bookmark by name
end_comment

begin_comment
comment|//        if (!xNamedBookmarks.hasByName(name)) {
end_comment

begin_comment
comment|//            return null;
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//        Object foundBookmark = xNamedBookmarks.getByName(name);
end_comment

begin_comment
comment|//        XTextContent xFoundBookmark = UnoRuntime.queryInterface(XTextContent.class, foundBookmark);
end_comment

begin_comment
comment|//        return xFoundBookmark.getAnchor();
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private XNameAccess getBookmarks() {
end_comment

begin_comment
comment|//        // query XBookmarksSupplier from document model and get bookmarks collection
end_comment

begin_comment
comment|//        XBookmarksSupplier xBookmarksSupplier = UnoRuntime.queryInterface(XBookmarksSupplier.class, xCurrentComponent);
end_comment

begin_comment
comment|//        XNameAccess xNamedBookmarks = xBookmarksSupplier.getBookmarks();
end_comment

begin_comment
comment|//        return xNamedBookmarks;
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    public void combineCiteMarkers(List<BibDatabase> databases, OOBibStyle style)
end_comment

begin_comment
comment|//            throws IOException, WrappedTargetException, NoSuchElementException, IllegalArgumentException,
end_comment

begin_comment
comment|//            UndefinedCharacterFormatException, UnknownPropertyException, PropertyVetoException, CreationException,
end_comment

begin_comment
comment|//            BibEntryNotFoundException {
end_comment

begin_comment
comment|//        XNameAccess nameAccess = getReferenceMarks();
end_comment

begin_comment
comment|//        // TODO: doesn't work for citations in footnotes/tables
end_comment

begin_comment
comment|//        List<String> names = getSortedReferenceMarks(nameAccess);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        final XTextRangeCompare compare = UnoRuntime.queryInterface(XTextRangeCompare.class, text);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        int piv = 0;
end_comment

begin_comment
comment|//        boolean madeModifications = false;
end_comment

begin_comment
comment|//        while (piv< (names.size() - 1)) {
end_comment

begin_comment
comment|//            XTextRange range1 = UnoRuntime.queryInterface(XTextContent.class, nameAccess.getByName(names.get(piv)))
end_comment

begin_comment
comment|//                    .getAnchor().getEnd();
end_comment

begin_comment
comment|//            XTextRange range2 = UnoRuntime.queryInterface(XTextContent.class, nameAccess.getByName(names.get(piv + 1)))
end_comment

begin_comment
comment|//                    .getAnchor().getStart();
end_comment

begin_comment
comment|//            if (range1.getText() != range2.getText()) {
end_comment

begin_comment
comment|//                piv++;
end_comment

begin_comment
comment|//                continue;
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//            XTextCursor mxDocCursor = range1.getText().createTextCursorByRange(range1);
end_comment

begin_comment
comment|//            mxDocCursor.goRight((short) 1, true);
end_comment

begin_comment
comment|//            boolean couldExpand = true;
end_comment

begin_comment
comment|//            while (couldExpand&& (compare.compareRegionEnds(mxDocCursor, range2)> 0)) {
end_comment

begin_comment
comment|//                couldExpand = mxDocCursor.goRight((short) 1, true);
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//            String cursorText = mxDocCursor.getString();
end_comment

begin_comment
comment|//            // Check if the string contains no line breaks and only whitespace:
end_comment

begin_comment
comment|//            if ((cursorText.indexOf('\n') == -1)&& cursorText.trim().isEmpty()) {
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//                // If we are supposed to set character format for citations, test this before
end_comment

begin_comment
comment|//                // making any changes. This way we can throw an exception before any reference
end_comment

begin_comment
comment|//                // marks are removed, preventing damage to the user's document:
end_comment

begin_comment
comment|//                if (style.isFormatCitations()) {
end_comment

begin_comment
comment|//                    XPropertySet xCursorProps = UnoRuntime.queryInterface(XPropertySet.class, mxDocCursor);
end_comment

begin_comment
comment|//                    String charStyle = style.getCitationCharacterFormat();
end_comment

begin_comment
comment|//                    try {
end_comment

begin_comment
comment|//                        xCursorProps.setPropertyValue(CHAR_STYLE_NAME, charStyle);
end_comment

begin_comment
comment|//                    } catch (UnknownPropertyException | PropertyVetoException | IllegalArgumentException |
end_comment

begin_comment
comment|//                            WrappedTargetException ex) {
end_comment

begin_comment
comment|//                        // Setting the character format failed, so we throw an exception that
end_comment

begin_comment
comment|//                        // will result in an error message for the user:
end_comment

begin_comment
comment|//                        throw new UndefinedCharacterFormatException(charStyle);
end_comment

begin_comment
comment|//                    }
end_comment

begin_comment
comment|//                }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//                List<String> keys = parseRefMarkName(names.get(piv));
end_comment

begin_comment
comment|//                keys.addAll(parseRefMarkName(names.get(piv + 1)));
end_comment

begin_comment
comment|//                removeReferenceMark(names.get(piv));
end_comment

begin_comment
comment|//                removeReferenceMark(names.get(piv + 1));
end_comment

begin_comment
comment|//                List<BibEntry> entries = new ArrayList<>();
end_comment

begin_comment
comment|//                for (String key : keys) {
end_comment

begin_comment
comment|//                    for (BibDatabase database : databases) {
end_comment

begin_comment
comment|//                        Optional<BibEntry> entry = database.getEntryByKey(key);
end_comment

begin_comment
comment|//                        if (entry.isPresent()) {
end_comment

begin_comment
comment|//                            entries.add(entry.get());
end_comment

begin_comment
comment|//                            break;
end_comment

begin_comment
comment|//                        }
end_comment

begin_comment
comment|//                    }
end_comment

begin_comment
comment|//                }
end_comment

begin_comment
comment|//                Collections.sort(entries, new FieldComparator(FieldName.YEAR));
end_comment

begin_comment
comment|//                String keyString = String.join(",", entries.stream().map(entry -> entry.getCiteKeyOptional().orElse(""))
end_comment

begin_comment
comment|//                        .collect(Collectors.toList()));
end_comment

begin_comment
comment|//                // Insert bookmark:
end_comment

begin_comment
comment|//                String bName = getUniqueReferenceMarkName(keyString, OOBibBase.AUTHORYEAR_PAR);
end_comment

begin_comment
comment|//                insertReferenceMark(bName, "tmp", mxDocCursor, true, style);
end_comment

begin_comment
comment|//                names.set(piv + 1, bName);
end_comment

begin_comment
comment|//                madeModifications = true;
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//            piv++;
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//        if (madeModifications) {
end_comment

begin_comment
comment|//            updateSortedReferenceMarks();
end_comment

begin_comment
comment|//            refreshCiteMarkers(databases, style);
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    public static XTextDocument selectComponent(List<XTextDocument> list)
end_comment

begin_comment
comment|//            throws UnknownPropertyException, WrappedTargetException, IndexOutOfBoundsException {
end_comment

begin_comment
comment|//        String[] values = new String[list.size()];
end_comment

begin_comment
comment|//        int ii = 0;
end_comment

begin_comment
comment|//        for (XTextDocument doc : list) {
end_comment

begin_comment
comment|//            values[ii] = String.valueOf(OOUtil.getProperty(doc.getCurrentController().getFrame(), "Title"));
end_comment

begin_comment
comment|//            ii++;
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//        JList<String> sel = new JList<>(values);
end_comment

begin_comment
comment|//        sel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
end_comment

begin_comment
comment|//        sel.setSelectedIndex(0);
end_comment

begin_comment
comment|//        int ans = JOptionPane.showConfirmDialog(null, new JScrollPane(sel), Localization.lang("Select document"),
end_comment

begin_comment
comment|//                JOptionPane.OK_CANCEL_OPTION);
end_comment

begin_comment
comment|//        if (ans == JOptionPane.OK_OPTION) {
end_comment

begin_comment
comment|//            return list.get(sel.getSelectedIndex());
end_comment

begin_comment
comment|//        } else {
end_comment

begin_comment
comment|//            return null;
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private static class ComparableMark implements Comparable<ComparableMark> {
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        private final String name;
end_comment

begin_comment
comment|//        private final Point position;
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        public ComparableMark(String name, Point position) {
end_comment

begin_comment
comment|//            this.name = name;
end_comment

begin_comment
comment|//            this.position = position;
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        @Override
end_comment

begin_comment
comment|//        public int compareTo(ComparableMark other) {
end_comment

begin_comment
comment|//            if (position.Y == other.position.Y) {
end_comment

begin_comment
comment|//                return position.X - other.position.X;
end_comment

begin_comment
comment|//            } else {
end_comment

begin_comment
comment|//                return position.Y - other.position.Y;
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        @Override
end_comment

begin_comment
comment|//        public boolean equals(Object o) {
end_comment

begin_comment
comment|//            if (this == o) {
end_comment

begin_comment
comment|//                return true;
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//            if (o instanceof ComparableMark) {
end_comment

begin_comment
comment|//                ComparableMark other = (ComparableMark) o;
end_comment

begin_comment
comment|//                return (this.position.X == other.position.X)&& (this.position.Y == other.position.Y)
end_comment

begin_comment
comment|//&& Objects.equals(this.name, other.name);
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//            return false;
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        public String getName() {
end_comment

begin_comment
comment|//            return name;
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        @Override
end_comment

begin_comment
comment|//        public int hashCode() {
end_comment

begin_comment
comment|//            return Objects.hash(position, name);
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    public BibDatabase generateDatabase(List<BibDatabase> databases)
end_comment

begin_comment
comment|//            throws NoSuchElementException, WrappedTargetException {
end_comment

begin_comment
comment|//        BibDatabase resultDatabase = new BibDatabase();
end_comment

begin_comment
comment|//        List<String> cited = findCitedKeys();
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        // For each cited key
end_comment

begin_comment
comment|//        for (String key : cited) {
end_comment

begin_comment
comment|//            // Loop through the available databases
end_comment

begin_comment
comment|//            for (BibDatabase loopDatabase : databases) {
end_comment

begin_comment
comment|//                Optional<BibEntry> entry = loopDatabase.getEntryByKey(key);
end_comment

begin_comment
comment|//                // If entry found
end_comment

begin_comment
comment|//                if (entry.isPresent()) {
end_comment

begin_comment
comment|//                    BibEntry clonedEntry = (BibEntry) entry.get().clone();
end_comment

begin_comment
comment|//                    // Insert a copy of the entry
end_comment

begin_comment
comment|//                    resultDatabase.insertEntry(clonedEntry);
end_comment

begin_comment
comment|//                    // Check if the cloned entry has a crossref field
end_comment

begin_comment
comment|//                    clonedEntry.getField(FieldName.CROSSREF).ifPresent(crossref -> {
end_comment

begin_comment
comment|//                        // If the crossref entry is not already in the database
end_comment

begin_comment
comment|//                        if (!resultDatabase.getEntryByKey(crossref).isPresent()) {
end_comment

begin_comment
comment|//                            // Add it if it is in the current library
end_comment

begin_comment
comment|//                            loopDatabase.getEntryByKey(crossref).ifPresent(resultDatabase::insertEntry);
end_comment

begin_comment
comment|//                        }
end_comment

begin_comment
comment|//                    });
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//                    // Be happy with the first found BibEntry and move on to next key
end_comment

begin_comment
comment|//                    break;
end_comment

begin_comment
comment|//                }
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        return resultDatabase;
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//}
end_comment

end_unit

