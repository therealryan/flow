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
				.expect( " tags are displayed", """
						┌─ Tags ───────────────────────────────────────────────────────────────────────┐
						│ bar baz foo oof                                                              │
						└──────────────────────────────────────────────────────────────────────────────┘""",
						"> " )
				.input( "+bar -oof\n" )
				.expect( "tag filters updated", """
						┌─ Tags ───────────────────────────────────────────────────────────────────────┐
						│ baz foo                                                                      │
						├─ Included ───────────────────────────────────────────────────────────────────┤
						│ bar                                                                          │
						├─ Excluded ───────────────────────────────────────────────────────────────────┤
						│ oof                                                                          │
						└──────────────────────────────────────────────────────────────────────────────┘""",
						"> " )
				.input( "r\t\n" )
				.expect( "tag filters reset", """
						┌─ Tags ───────────────────────────────────────────────────────────────────────┐
						│ bar baz foo oof                                                              │
						└──────────────────────────────────────────────────────────────────────────────┘""",
						"> " )
				.expectFlowConstruction( "No flow access yet",
						false )
				// proceed to index phase
				.input( "\n" )
				.expect( "flows displayed", """
						┌─ Flows ──────────────────────────────────────────────────────────────────────┐
						│ 1 first flow bar foo                                                         │
						│ 2 second flow bar baz                                                        │
						│ 3 third flow baz oof                                                         │
						└──────────────────────────────────────────────────────────────────────────────┘
						┌─ Tags ───────────────────────────────────────────────────────────────────────┐
						│ bar baz foo oof                                                              │
						└──────────────────────────────────────────────────────────────────────────────┘""",
						"> " )
				.expectFlowConstruction( "we're now displaying flows, so we have to construct them",
						true )
				.input( "-foo +1\n" )
				.expect( "flow selected", """
						┌─ Flows ──────────────────────────────────────────────────────────────────────┐
						├─ Disabled ───────────────────────────────────────────────────────────────────┤
						│ 2 third flow baz oof                                                         │
						├─ Enabled ────────────────────────────────────────────────────────────────────┤
						│ 1 second flow bar baz                                                        │
						└──────────────────────────────────────────────────────────────────────────────┘
						┌─ Tags ───────────────────────────────────────────────────────────────────────┐
						│ bar baz oof                                                                  │
						├─ Excluded ───────────────────────────────────────────────────────────────────┤
						│ foo                                                                          │
						└──────────────────────────────────────────────────────────────────────────────┘""",
						"> " )
				.input( "fl\t\n" )
				.expect( "flow choice reset", """
						┌─ Flows ──────────────────────────────────────────────────────────────────────┐
						│ 1 second flow bar baz                                                        │
						│ 2 third flow baz oof                                                         │
						└──────────────────────────────────────────────────────────────────────────────┘
						┌─ Tags ───────────────────────────────────────────────────────────────────────┐
						│ bar baz oof                                                                  │
						├─ Excluded ───────────────────────────────────────────────────────────────────┤
						│ foo                                                                          │
						└──────────────────────────────────────────────────────────────────────────────┘""",
						"> " )
				.input( "t\t\n" )
				.expect( "tag filters reset", """
						┌─ Flows ──────────────────────────────────────────────────────────────────────┐
						│ 1 first flow bar foo                                                         │
						│ 2 second flow bar baz                                                        │
						│ 3 third flow baz oof                                                         │
						└──────────────────────────────────────────────────────────────────────────────┘
						┌─ Tags ───────────────────────────────────────────────────────────────────────┐
						│ bar baz foo oof                                                              │
						└──────────────────────────────────────────────────────────────────────────────┘""",
						"> " )
				.input( "-foo +1\n" )
				.expect( "flow selected again", """
						┌─ Flows ──────────────────────────────────────────────────────────────────────┐
						├─ Disabled ───────────────────────────────────────────────────────────────────┤
						│ 2 third flow baz oof                                                         │
						├─ Enabled ────────────────────────────────────────────────────────────────────┤
						│ 1 second flow bar baz                                                        │
						└──────────────────────────────────────────────────────────────────────────────┘
						┌─ Tags ───────────────────────────────────────────────────────────────────────┐
						│ bar baz oof                                                                  │
						├─ Excluded ───────────────────────────────────────────────────────────────────┤
						│ foo                                                                          │
						└──────────────────────────────────────────────────────────────────────────────┘""",
						"> " )
				.input( "r\t\n" )
				.expect( "all filters reset", """
						┌─ Flows ──────────────────────────────────────────────────────────────────────┐
						│ 1 first flow bar foo                                                         │
						│ 2 second flow bar baz                                                        │
						│ 3 third flow baz oof                                                         │
						└──────────────────────────────────────────────────────────────────────────────┘
						┌─ Tags ───────────────────────────────────────────────────────────────────────┐
						│ bar baz foo oof                                                              │
						└──────────────────────────────────────────────────────────────────────────────┘""",
						"> " )
				.input( "\n" )
				.on( mdl )
				.expectResults(
						"first flow [bar, foo]",
						"second flow [bar, baz]",
						"third flow [baz, oof]" );
	}
}
