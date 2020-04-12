import React from 'react';
import PropTypes from 'prop-types';

import ErrorMessage from './error-message.js';

class ErrorBoundary extends React.Component {
    static getDerivedStateFromError(error) {
    // Update state so the next render will show the fallback UI.
        return {hasError: true};
    }
    constructor(props) {
        super(props);
        this.state = {hasError: false};
    }

    componentDidCatch(error, info) {
    // You can also log the error to an error reporting service
    }

    render() {
        if (this.state.hasError) {
            return <ErrorMessage />;
        }
        return this.props.children;
    }
}

ErrorBoundary.propTypes = {
    children: PropTypes.oneOfType([
        PropTypes.element,
        PropTypes.array
    ])
};

export default ErrorBoundary;
