package com.mastercard.test.flow.assrt.filter.cli;

import org.junit.jupiter.api.Test;

import com.mastercard.test.flow.assrt.filter.mock.Mdl;

/**
 * Exercises the state-reset functions
 */
@SuppressWarnings("static-method")
class ResetTest extends AbstractFilterTest {

	/**
	 * Exercise the reset commands
	 */
	@Test
	void reset() {

		Mdl mdl = new Mdl().withFlows(
				"first flow [foo, bar]",
				"second flow [bar, baz]",
				"third flow [baz, oof]" );

		// run the cli
		new FilterCliHarness()
				.expect( " tags are displayed", ""
						+ "┌─ Tags ───────────────────────────────────────────────────────────────────────┐\n"
						+ "│ bar baz foo oof                                                              │\n"
						+ "└──────────────────────────────────────────────────────────────────────────────┘",
						"> " )
				.input( "+bar -oof\n" )
				.expect( "tag filters updated", ""
						+ "┌─ Tags ───────────────────────────────────────────────────────────────────────┐\n"
						+ "│ baz foo                                                                      │\n"
						+ "├─ Included ───────────────────────────────────────────────────────────────────┤\n"
						+ "│ bar                                                                          │\n"
						+ "├─ Excluded ───────────────────────────────────────────────────────────────────┤\n"
						+ "│ oof                                                                          │\n"
						+ "└──────────────────────────────────────────────────────────────────────────────┘",
						"> " )
				.input( "r\t\n" )
				.expect( "tag filters reset", ""
						+ "┌─ Tags ───────────────────────────────────────────────────────────────────────┐\n"
						+ "│ bar baz foo oof                                                              │\n"
						+ "└──────────────────────────────────────────────────────────────────────────────┘",
						"> " )
				.expectFlowConstruction( "No flow access yet",
						false )
				// proceed to index phase
				.input( "\n" )
				.expect( "flows displayed", ""
						+ "┌─ Flows ──────────────────────────────────────────────────────────────────────┐\n"
						+ "│ 1 first flow bar foo                                                         │\n"
						+ "│ 2 second flow bar baz                                                        │\n"
						+ "│ 3 third flow baz oof                                                         │\n"
						+ "└──────────────────────────────────────────────────────────────────────────────┘\n"
						+ "┌─ Tags ───────────────────────────────────────────────────────────────────────┐\n"
						+ "│ bar baz foo oof                                                              │\n"
						+ "└──────────────────────────────────────────────────────────────────────────────┘",
						"> " )
				.expectFlowConstruction( "we're now displaying flows, so we have to construct them",
						true )
				.input( "-foo +1\n" )
				.expect( "flow selected", ""
						+ "┌─ Flows ──────────────────────────────────────────────────────────────────────┐\n"
						+ "├─ Disabled ───────────────────────────────────────────────────────────────────┤\n"
						+ "│ 2 third flow baz oof                                                         │\n"
						+ "├─ Enabled ────────────────────────────────────────────────────────────────────┤\n"
						+ "│ 1 second flow bar baz                                                        │\n"
						+ "└──────────────────────────────────────────────────────────────────────────────┘\n"
						+ "┌─ Tags ───────────────────────────────────────────────────────────────────────┐\n"
						+ "│ bar baz oof                                                                  │\n"
						+ "├─ Excluded ───────────────────────────────────────────────────────────────────┤\n"
						+ "│ foo                                                                          │\n"
						+ "└──────────────────────────────────────────────────────────────────────────────┘",
						"> " )
				.input( "fl\t\n" )
				.expect( "flow choice reset", ""
						+ "┌─ Flows ──────────────────────────────────────────────────────────────────────┐\n"
						+ "│ 1 second flow bar baz                                                        │\n"
						+ "│ 2 third flow baz oof                                                         │\n"
						+ "└──────────────────────────────────────────────────────────────────────────────┘\n"
						+ "┌─ Tags ───────────────────────────────────────────────────────────────────────┐\n"
						+ "│ bar baz oof                                                                  │\n"
						+ "├─ Excluded ───────────────────────────────────────────────────────────────────┤\n"
						+ "│ foo                                                                          │\n"
						+ "└──────────────────────────────────────────────────────────────────────────────┘",
						"> " )
				.input( "t\t\n" )
				.expect( "tag filters reset", ""
						+ "┌─ Flows ──────────────────────────────────────────────────────────────────────┐\n"
						+ "│ 1 first flow bar foo                                                         │\n"
						+ "│ 2 second flow bar baz                                                        │\n"
						+ "│ 3 third flow baz oof                                                         │\n"
						+ "└──────────────────────────────────────────────────────────────────────────────┘\n"
						+ "┌─ Tags ───────────────────────────────────────────────────────────────────────┐\n"
						+ "│ bar baz foo oof                                                              │\n"
						+ "└──────────────────────────────────────────────────────────────────────────────┘",
						"> " )
				.input( "-foo +1\n" )
				.expect( "flow selected again", ""
						+ "┌─ Flows ──────────────────────────────────────────────────────────────────────┐\n"
						+ "├─ Disabled ───────────────────────────────────────────────────────────────────┤\n"
						+ "│ 2 third flow baz oof                                                         │\n"
						+ "├─ Enabled ────────────────────────────────────────────────────────────────────┤\n"
						+ "│ 1 second flow bar baz                                                        │\n"
						+ "└──────────────────────────────────────────────────────────────────────────────┘\n"
						+ "┌─ Tags ───────────────────────────────────────────────────────────────────────┐\n"
						+ "│ bar baz oof                                                                  │\n"
						+ "├─ Excluded ───────────────────────────────────────────────────────────────────┤\n"
						+ "│ foo                                                                          │\n"
						+ "└──────────────────────────────────────────────────────────────────────────────┘",
						"> " )
				.input( "r\t\n" )
				.expect( "all filters reset", ""
						+ "┌─ Flows ──────────────────────────────────────────────────────────────────────┐\n"
						+ "│ 1 first flow bar foo                                                         │\n"
						+ "│ 2 second flow bar baz                                                        │\n"
						+ "│ 3 third flow baz oof                                                         │\n"
						+ "└──────────────────────────────────────────────────────────────────────────────┘\n"
						+ "┌─ Tags ───────────────────────────────────────────────────────────────────────┐\n"
						+ "│ bar baz foo oof                                                              │\n"
						+ "└──────────────────────────────────────────────────────────────────────────────┘",
						"> " )
				.input( "\n" )
				.on( mdl )
				.expectResults(
						"first flow [bar, foo]",
						"second flow [bar, baz]",
						"third flow [baz, oof]" );
	}
}
