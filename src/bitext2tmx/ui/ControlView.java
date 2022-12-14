/*
#######################################################################
#
#  bitext2tmx - Bitext Aligner/TMX Editor
#
#  Copyright (C) 2006-2009  Raymond: Martin
#
#  This program is free software; you can redistribute it and/or modify
#  it under the terms of the GNU General Public License as published by
#  the Free Software Foundation; either version 2 of the License, or
#  (at your option) any later version.
#
#  This program is distributed in the hope that it will be useful,
#  but WITHOUT ANY WARRANTY; without even the implied warranty of
#  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#  GNU General Public License for more details.
#
#  You should have received a copy of the GNU General Public License
#  along with this program; if not, write to the Free Software
#  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
#
#######################################################################
*/


package bitext2tmx.ui;

import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import com.vlsolutions.swing.docking.*;

import static org.openide.awt.Mnemonics.*;

import static bitext2tmx.util.Localization.l10n;
import static bitext2tmx.util.Utilities.*;


/**
 *   Alignment Table view for parallel texts
 *
 */
final class ControlsView extends DockablePanel implements ActionListener
{
  final private Bitext2tmxWindow _wndB2T;

  final private JButton  _btnOriginalDelete    = new JButton();
  final private JButton  _btnOriginalJoin      = new JButton();
  final private JButton  _btnOriginalSplit     = new JButton();
  final private JButton  _btnTranslationDelete = new JButton();
  final private JButton  _btnTranslationJoin   = new JButton();
  final private JButton  _btnTranslationSplit  = new JButton();
  final private JButton  _btnRemoveBlankRows   = new JButton();
  final private JButton  _btnTUSplit           = new JButton();

  final private JButton  _btnUndo              = new JButton();

  final private JPanel pnlButtons       = new JPanel( new GridLayout( 2, 1 ) );
  final private JPanel pnlButtonsTop    = new JPanel( new GridLayout( 1, 2 ) );
  final private JPanel pnlButtonsBottom = new JPanel();
  final private JPanel pnlButtonsLeft   = new JPanel();
  final private JPanel pnlButtonsRight  = new JPanel();

  public ControlsView( final Bitext2tmxWindow wndB2T )
  {
    super( "SegmentButtonsView" );

    _wndB2T = wndB2T;

    //  ToDo: l10n
    getDockKey().setName( "Controls" );
    getDockKey().setTooltip( "Alignment/Segment Controls" );
    getDockKey().setCloseEnabled( true );
    getDockKey().setAutoHideEnabled( true );
    getDockKey().setResizeWeight( 1.0f );  // takes all resizing
    getDockKey().setIcon( getIcon( "b2t-icon-small.png", this ) );

    setLayout( new BorderLayout() );

    setLocalizedText( _btnRemoveBlankRows, l10n( "BTN.DELETE.BLANK.ROWS" ) );
    _btnRemoveBlankRows.setToolTipText( l10n( "BTN.DELETE.BLANK.ROWS.TOOLTIP" ) );
    _btnRemoveBlankRows.addActionListener( this );
    _btnRemoveBlankRows.setEnabled( false );

    setLocalizedText( _btnTUSplit, l10n( "BTN.SPLIT.TU" ) );
    _btnTUSplit.setToolTipText( l10n( "BTN.SPLIT.TU.TOOLTIP" ) );
    _btnTUSplit.addActionListener( this );
    _btnTUSplit.setEnabled( false );

    setLocalizedText( _btnTranslationJoin, l10n( "BTN.JOIN.TRANSLATION" ) );
    _btnTranslationJoin.addActionListener( this );
    _btnTranslationJoin.setActionCommand( l10n( "BTN.JOIN.TRANSLATION" ) );

    setLocalizedText( _btnOriginalJoin, l10n( "BTN.JOIN.ORIGINAL" ) );
    _btnOriginalJoin.addActionListener( this );
    _btnOriginalJoin.setActionCommand( l10n( "BTN.JOIN.ORIGINAL" ) );

    setLocalizedText( _btnTranslationDelete, l10n( "BTN.DELETE.TRANSLATION" ) );
    _btnTranslationDelete.addActionListener( this );
    _btnTranslationDelete.setActionCommand( l10n( "BTN.DELETE.TRANSLATION" ) );

    setLocalizedText( _btnOriginalDelete, l10n( "BTN.DELETE.ORIGINAL" ) );
    _btnOriginalDelete.addActionListener( this );
    _btnOriginalDelete.setActionCommand( l10n( "BTN.DELETE.ORIGINAL" ) );

    setLocalizedText( _btnTranslationSplit, l10n( "BTN.SPLIT.TRANSLATION" ) );
    _btnTranslationSplit.addActionListener( this );
    _btnTranslationSplit.setActionCommand( l10n( "BTN.SPLIT.TRANSLATION" ) );

    setLocalizedText( _btnOriginalSplit, l10n( "BTN.SPLIT.ORIGINAL" ) );
    _btnOriginalSplit.addActionListener( this );
    _btnOriginalSplit.setActionCommand( l10n( "BTN.SPLIT.ORIGINAL" ) );

    _btnUndo.setText( l10n( "BTN.UNDO" ) );
    setLocalizedText( _btnUndo, l10n( "BTN.UNDO" ) );
    _btnUndo.addActionListener( this );
    _btnUndo.setEnabled( false );

    enableButtons( false );

    pnlButtonsBottom  .add( _btnUndo,              null );
    pnlButtonsBottom  .add( _btnRemoveBlankRows,   null );
    pnlButtonsBottom  .add( _btnTUSplit,           null );
    pnlButtonsLeft    .add( _btnOriginalJoin,      null );
    pnlButtonsLeft    .add( _btnOriginalDelete,    null );
    pnlButtonsLeft    .add( _btnOriginalSplit,     null );
    pnlButtonsRight   .add( _btnTranslationJoin,   null );
    pnlButtonsRight   .add( _btnTranslationDelete, null );
    pnlButtonsRight   .add( _btnTranslationSplit,  null );

    pnlButtonsTop.add( pnlButtonsLeft );
    pnlButtonsTop.add( pnlButtonsRight );

    pnlButtons.add( pnlButtonsBottom );
    pnlButtons.add( pnlButtonsTop );

    pnlButtons.setMinimumSize( new Dimension( 480, 120 ) );

    add( pnlButtons, BorderLayout.CENTER );
  }

  final void setFonts( final Font font )
  {
    _btnUndo               .setFont( font );
    _btnRemoveBlankRows    .setFont( font );
    _btnTUSplit            .setFont( font );
    _btnOriginalJoin       .setFont( font );
    _btnOriginalDelete     .setFont( font );
    _btnOriginalSplit      .setFont( font );
    _btnTranslationJoin    .setFont( font );
    _btnTranslationDelete  .setFont( font );
    _btnTranslationSplit   .setFont( font );
  }

  final void enableButtons( boolean bEnabled )
  {
    _btnRemoveBlankRows    .setEnabled( bEnabled );
    _btnTUSplit            .setEnabled( bEnabled );
    _btnOriginalJoin       .setEnabled( bEnabled );
    _btnOriginalDelete     .setEnabled( bEnabled );
    _btnOriginalSplit      .setEnabled( bEnabled );
    _btnTranslationJoin    .setEnabled( bEnabled );
    _btnTranslationDelete  .setEnabled( bEnabled );
    _btnTranslationSplit   .setEnabled( bEnabled );
  }

  final public void setUndoEnabled( boolean bEnabled )
  { _btnUndo.setEnabled( bEnabled ); }

  final public void setOriginalJoinEnabled( boolean bEnabled )
  { _btnOriginalJoin.setEnabled( bEnabled ); }

  final public void setTranslationJoinEnabled( boolean bEnabled )
  { _btnTranslationJoin.setEnabled( bEnabled ); }

  final public void updateText()
  {
    _btnUndo.setText( l10n( "BTN.UNDO" ) );

    _btnRemoveBlankRows.setText( l10n( "BTN.DELETE.BLANK.ROWS" ) );
    _btnRemoveBlankRows.setToolTipText( l10n( "BTN.DELETE.BLANK.ROWS.TOOLTIP" ) );
    _btnTUSplit.setText( l10n( "BTN.SPLIT.TU" ) );
    _btnTUSplit.setToolTipText( l10n( "BTN.SPLIT.TU.TOOLTIP" ) );

    _btnOriginalJoin    .setActionCommand( l10n( "BTN.JOIN" ) );
    _btnOriginalJoin    .setText( l10n( "BTN.JOIN" ) );
    _btnOriginalDelete  .setActionCommand( l10n( "BTN.DELETE" ) );
    _btnOriginalDelete  .setText( l10n( "BTN.DELETE" ) );
    _btnOriginalSplit   .setActionCommand( l10n( "BTN.SPLIT" ) );
    _btnOriginalSplit   .setText( l10n( "BTN.SPLIT" ) );

    _btnTranslationJoin    .setActionCommand( l10n( "BTN.JOIN" ) );
    _btnTranslationJoin    .setText( l10n( "BTN.JOIN" ) );
    _btnTranslationDelete  .setText( l10n( "BTN.DELETE" ) );
    _btnTranslationDelete  .setActionCommand( l10n( "BTN.DELETE" ) );
    _btnTranslationSplit   .setText( l10n( "BTN.SPLIT" ) );
    _btnTranslationSplit   .setActionCommand( l10n( "BTN.SPLIT" ) );
  }

  final private void onUndo()            { _wndB2T.onUndo(); }
  final private void onRemoveBlankRows() { _wndB2T.onRemoveBlankRows(); }

  final private void onTUSplit()
  {
    _wndB2T.onTUSplit();
    _btnUndo.setEnabled( true );
  }

  final private void onOriginalJoin()
  {
    _wndB2T.onOriginalJoin();
    _btnUndo.setEnabled( true );
  }

  final private void onOriginalDelete()
  {
    _wndB2T.onOriginalDelete();
    _btnUndo.setEnabled( true );
  }

  final private void onOriginalSplit()
  {
    _wndB2T.onOriginalSplit();
    _btnUndo.setEnabled( true );
  }

  final private void onTranslationJoin()
  {
    _wndB2T.onTranslationJoin();
    _btnUndo.setEnabled( true );
  }

  final private void onTranslationDelete()
  {
    _wndB2T.onTranslationDelete();
    _btnUndo.setEnabled( true );
  }

  final private void onTranslationSplit()
  {
    _wndB2T.onTranslationSplit();
    _btnUndo.setEnabled( true );
  }

  final public void actionPerformed( final ActionEvent action )
  {
    final Object actor = action.getSource();

    if( actor instanceof JButton )
    {
      if( actor == _btnOriginalDelete )          onOriginalDelete();
      else if( actor == _btnOriginalJoin )       onOriginalJoin();
      else if( actor == _btnOriginalSplit )      onOriginalSplit();

      else if( actor == _btnTranslationDelete )  onTranslationDelete();
      else if( actor == _btnTranslationJoin )    onTranslationJoin();
      else if( actor == _btnTranslationSplit )   onTranslationSplit();

      else if( actor == _btnRemoveBlankRows )    onRemoveBlankRows();
      else if( actor == _btnTUSplit )            onTUSplit();
      else if( actor == _btnUndo )               onUndo();
    }
  }

}//  ControlView{}


